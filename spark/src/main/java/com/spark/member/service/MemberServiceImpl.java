package com.spark.member.service;

import java.util.*;

import com.spark.member.dto.*;
import com.spark.member.dto.request.*;
import com.spark.member.dto.response.*;
import com.spark.member.model.Member;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.base.util.JwtProvider;
import com.spark.member.repository.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberValidator memberValidtor;
    private final TokenResponse tokenResponse;
    private final MemberPreprocessor memberPreprocessor;

    @Override
    public LoginResult login(LoginRequest m) {

        // 1. 로그인 정보 조회
        Member result = memberDao.login(m.getMemId()).orElseThrow(() -> new CustomException("회원정보가 없습니다.", 400));

        // 비밀번호 유효성 검사
        result.validationPassword(m.getMemPwd(), passwordEncoder);

        // DB에서 조회한 회원 정보 LoginResponse로 빌드
        LoginResponse response = LoginResponse.from(result);

        // 토큰 생성
        return tokenResponse.CreateToken(response);

    }


    @Override
    public ValidResponse loginUserInfo(TokenRequest authHeader) {
        // 토큰 유효성 검사 및 회원 정보 조회
        String validateToken = authHeader.getTokenRequest().replace("Bearer ", "");
        memberValidtor.validToken(validateToken);

        // 토큰에서 사용자 아이디 추출
        String memId = jwtProvider.getUserId(validateToken);
        // 사용자 아이디로 회원 정보 조회
        Member member = memberDao.loginUserInfo(memId);

        if (member == null) {
            throw new CustomException("해당 사용자가 존재하지 않습니다.", 403);
        }

        // 회원 정보로 LoginResponse 빌드
        LoginResponse response = LoginResponse.from(member);

        return ValidResponse.available(response);

    }

    @Override
    public Member findById(String memId) {

        // 사용자 아이디로 회원 정보 조회
        return memberDao.findById(memId)
            .orElseThrow(() -> new CustomException("해당 사용자가 존재하지 않습니다.", 403));

    }

    @Override
    public TokenResult insertRefreshToken(TokenRequest refreshTokenHeader) {

        // 헤더에서 Bearer 제거 후 토큰 추출
        String refreshToken = refreshTokenHeader.getTokenRequest().replace("Bearer ", "");

        // 토큰 유효성 검사
        memberValidtor.validToken(refreshToken);

        // 리프레시 토큰 DB에 저장
        return tokenResponse.insertRefreshToken(refreshToken);

    }

    @Override
    public LogoutResult deleteToken(TokenRequest refreshTokenHeader) {

        // 헤더에서 Bearer 제거 후 토큰 추출
        String token = refreshTokenHeader.getTokenRequest().replace("Bearer ", "");

        // 토큰 유효성 검사
        memberValidtor.validToken(token);

        // 토큰 삭제
        return tokenResponse.deleteToken(token);
    }


    @Override
    public String smsCode(PhoneRequest phone) {

        // 가입된 사용자인지 먼저 검사
        Optional<Member> optional = Optional.ofNullable(findById(phone.getPhone()));

        // 이미 가입된 사용자라면 예외 처리
        if (optional.isPresent()) {
            throw new CustomException("현재 가입된 사용자입니다.", 409);
        }

        // 쿨 SMS API를 통해 인증 코드 전송
        return memberPreprocessor.coolSms(phone);

    }

    @Override
    public List<RecommendResponse> recommendList(RecommendRequest recommendRequest) {

        // Member로 빌드
        Member member = recommendRequest.toDomain();

        // 추천 리스트 조회
        List<Member> list = memberDao.recommendList(member);

        // 추천 리스트가 비어있거나 null인 경우 예외 처리
        if (list == null) {
            throw new CustomException("추천 리스트 불러오기에 실패하였습니다.", 500);
        }

        // 추천 리스트 순서 섞기
        Collections.shuffle(list);

        // 추천 리스트에서 최대 50개만 추출
        List<Member> result = list.subList(0, Math.min(50, list.size()));

        // MemberDto를 RecommendResponse로 변환
        return result.stream()
            .map(RecommendResponse::from)
            .toList();
    }

    @Override
    public int signUp(SignUpRequest m) {

        // Member로 빌드
        Member member = m.toDomain();

        // 비밀번호 암호화
        member.encryptPassword(passwordEncoder);

        // 회원 가입 성공 체크
        int result = memberDao.signUp(member);
        if (result == 0) {
            throw new CustomException("회원 가입에 실패하였습니다", 500);
        }


        return result;
    }

    @Override
    public LoginResponse insertInfo(InsertMemberInfoRequest insertMemberInfo, MultipartFile uploadFile) {

        Member member = insertMemberInfo.toDomain();

        int result = memberDao.insertInfo(member);
        if (result == 0) {
            throw new CustomException("회원 정보 추가에 실패하였습니다.", 500);
        }

        // 회원 정보 조회
        Member memInfo = memberDao.login(member.getMemId()).orElseThrow(() -> new CustomException("회원 정보 불러오기가 실패하였습니다.", 500));

        return LoginResponse.from(memInfo);
    }


    @Override
    public int recommendDelete(RecommendDeleteRequest recommendDelete) {

        int result = memberDao.recommendDelete(recommendDelete);
        if (result == 0) {
            throw new CustomException("추천 목록 삭제에 실패하였습니다.", 500);
        }
        return result;

    }

    @Override
    public int likeMember(LikeSendRequest likeSend) {

        // 좋아요 테이블에 이미 저장되어있는지 확인
        LikeDto result = memberDao.likeMemberCheck(likeSend);

        if (result != null) {
            throw new CustomException("이미 좋아요를 누른상태입니다.", 401);
        } else {
            return memberDao.likeMember(likeSend);
        }

    }

    @Override
    public boolean duplicateCheck(DuplicateCheckRequest duplicateCheck) {

        try {
            // 닉네임 중복 검사
            int result = memberDao.duplicateCheck(duplicateCheck);
            return result <= 0;
        } catch (Exception e) {
            throw new CustomException("닉네임 중복 검사에 실패했습니다.", 500);
        }

    }

    @Override
    public int interestMem(InterestMemberAddRequest interestMemberAdd) {

        // 관심 회원 등록 전에 이미 등록된 회원인지 확인
        int check = memberDao.interestMemCheck(interestMemberAdd);
        if (check > 0) {
            throw new CustomException("이미 관심이 등록된 회원입니다.", 409);
        }

        // 관심 회원 등록
        int result = memberDao.interestMem(interestMemberAdd);
        if (result == 0) {
            throw new CustomException("관심 회원 등록에 실패하였습니다.", 500);
        }
        return result;
    }

    @Override
    public DetailMemberInfoResponse detailInfo(DetailMemberInfoRequest detailMemberInfo) {

        // 상태방의 상세정보 조회
        Member result = memberDao.detailInfo(detailMemberInfo).orElseThrow(() -> new CustomException("해당 회원이 존재하지 않습니다.", 400));

        // 상세정보 응답 빌드
        return DetailMemberInfoResponse.from(result);

    }

    @Override
    public List<LikeListResponse> likeList(LikeListRequest likeList) {

        // 로그인한 사용자의 좋아요 목록 조회
        List<Member> result = memberDao.likeList(likeList);
        if (result == null) {
            throw new CustomException("좋아요 목록 조회에 실패하였습니다.", 500);
        }

        return result.stream()
            .map(LikeListResponse::from)
            .toList();

    }

    @Override
    public List<InterestListResponse> interestList(InterestListRequest interestList) {


        List<Member> result = memberDao.interestList(interestList);

        return result.stream()
            .map(InterestListResponse::from)
            .toList();
    }

    @Override
    public Integer likeYes(LikeRequest likeInfo) {

        int result = memberDao.likeYes(likeInfo); // 좋아요 수락 처리

        if (result > 0) {
            // 좋아요 수락 후 채팅방 생성 로직 추가
            return result;
        } else {
            throw new CustomException("좋아요 수락에 실패하였습니다.", 500);
        }
    }

    @Override
    public Integer likeNo(LikeRequest likeInfo) {

        int result = memberDao.likeNo(likeInfo); // 좋아요 거절 처리
        if (result > 0) {
            return result;
        } else {
            throw new CustomException("좋아요 거절에 실패하였습니다.", 500);
        }
    }


}
