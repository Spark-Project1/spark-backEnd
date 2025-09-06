package com.spark.member.service;

import java.util.*;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.chat.model.Chat;
import com.spark.chat.repository.ChatDao;
import com.spark.member.common.Character;
import com.spark.member.common.Interest;
import com.spark.member.common.Tendencies;
import com.spark.member.dto.*;
import com.spark.member.dto.request.*;
import com.spark.member.dto.response.*;
import com.spark.member.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.exception.CustomException;
import com.spark.base.util.JwtProvider;
import com.spark.member.repository.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberValidator memberValidtor;
    private final TokenResponse tokenResponse;
    private final MemberPreprocessor memberPreprocessor;
    private final ChatDao chatDao;

    @Override
    public LoginResult login(LoginRequest m) {

        // 1. 로그인 정보 조회
        Member member = memberDao.login(m.getMemId()).orElseThrow(() -> new SparkException(SparkErrorCode.SPARK_100)); // 존재하지않는 회원시 에러처리

        // 비밀번호 유효성 검사
        member.validationPassword(m.getMemPwd(), passwordEncoder);

        // DB에서 조회한 회원 정보 LoginResponse로 빌드
        LoginResponse response = LoginResponse.from(member);

        // 토큰 생성
        return tokenResponse.CreateToken(response);

    }


    @Override
    public Member findById(String memId) {
        // 사용자 아이디로 회원 정보 조회
        return memberDao.findById(memId)
            .orElseThrow(() -> new SparkException(SparkErrorCode.SPARK_100)); // 존재하지 않는 회원시 에러 처리

    }

    @Override
    public TokenResult insertRefreshToken(TokenRequest refreshTokenHeader) {

        // 토큰 유효성 검사
        memberValidtor.validToken(refreshTokenHeader.getTokenRequest());

        // 리프레시 토큰 DB에 저장
        return tokenResponse.insertRefreshToken(refreshTokenHeader.getTokenRequest());

    }

    @Override
    public LogoutResult deleteToken(TokenRequest refreshTokenHeader) {

        // 토큰 유효성 검사
        memberValidtor.validToken(refreshTokenHeader.getTokenRequest());

        // 토큰 삭제
        return tokenResponse.deleteToken(refreshTokenHeader.getTokenRequest());
    }


    @Override
    public String smsCode(PhoneRequest phone) {

        // 가입된 사용자인지 먼저 검사
        Optional<Member> optional = Optional.ofNullable(findById(phone.getPhone()));

        // 이미 가입된 사용자라면 예외 처리
        if (optional.isPresent()) {
            throw new SparkException(SparkErrorCode.SPARK_102);
        }

        // 쿨 SMS API를 통해 인증 코드 전송
        return memberPreprocessor.coolSms(phone);

    }

    @Override
    public List<RecommendResponse> recommendList(RecommendRequest recommendRequest) {

        recommendRequest.toArray();


        List<Member> list = memberDao.recommendList(recommendRequest);

        // 추천 리스트가 비어있거나 null인 경우 예외 처리
        if (list == null) {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
        if (list.isEmpty()) {
            // 빈 리스트 반환
            return Collections.emptyList();
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

        // 임시 Member 생성
        Member member = Member.tempMember(m.getMemId(), m.getMemPwd());
        // 비밀번호 암호화
        member.encryptPassword(passwordEncoder);

        // 회원 가입 성공 체크
        int result = memberDao.signUp(member);
        if (result == 0) {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }

        return result;
    }

    @Override
    public LoginResponse insertInfo(UpdateMemberInfoRequest insertMemberInfo, MultipartFile uploadFile, Member member) {

        member.updateMember(insertMemberInfo);
        member.statusActive();
        memberPreprocessor.uploadProfileImg(uploadFile, member);
        int result = memberDao.insertInfo(member);
        if (result == 0) {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }

        memberPreprocessor.insertMemberAttributes(member.getMemId(),insertMemberInfo.getInterest().stream().map(Interest::name).toList(),"interest");

        memberPreprocessor.insertMemberAttributes(member.getMemId(),insertMemberInfo.getTendencies().stream().map(Tendencies::name).toList(),"tendencies");

        memberPreprocessor.insertMemberAttributes(member.getMemId(),insertMemberInfo.getCharacter().stream().map(Character::name).toList(),"character");


        return LoginResponse.from(member);
    }


    @Override
    public int recommendDelete(RecommendDeleteRequest recommendDelete) {

        int result = memberDao.recommendDelete(recommendDelete);
        if (result == 0) {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
        return result;

    }

    @Override
    public int likeMember(LikeSendRequest likeSend) {

        // 좋아요 테이블에 이미 저장되어있는지 확인
        LikeDto result = memberDao.likeMemberCheck(likeSend);

        if (result != null) {
            throw new SparkException(SparkErrorCode.SPARK_110);
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
            throw new SparkException(SparkErrorCode.SPARK_999);
        }

    }

    @Override
    public int interestMem(InterestMemberAddRequest interestMemberAdd) {

        // 관심 회원 등록 전에 이미 등록된 회원인지 확인
        int check = memberDao.interestMemCheck(interestMemberAdd);
        if (check > 0) {
            throw new SparkException(SparkErrorCode.SPARK_110);
        }

        // 관심 회원 등록
        int result = memberDao.interestMem(interestMemberAdd);
        if (result == 0) {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
        return result;
    }

    @Override
    public DetailMemberInfoResponse detailInfo(DetailMemberInfoRequest detailMemberInfo) {

        // 상태방의 상세정보 조회
        Member result = memberDao.detailInfo(detailMemberInfo).orElseThrow(() -> new SparkException(SparkErrorCode.SPARK_100));

        // 상세정보 응답 빌드
        return DetailMemberInfoResponse.from(result);

    }

    @Override
    public List<LikeListResponse> likeList(LikeListRequest likeList) {

        // 로그인한 사용자의 좋아요 목록 조회
        List<Member> result = memberDao.likeList(likeList);
        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }

        return result.stream()
            .map(LikeListResponse::from)
            .toList();

    }

    @Override
    public List<InterestListResponse> interestList(InterestListRequest interestList) {


        List<Member> result = memberDao.interestList(interestList);
        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }
        return result.stream()
            .map(InterestListResponse::from)
            .toList();
    }

    @Override
    public Integer likeYes(LikeRequest likeInfo) {

        int result = memberDao.likeYes(likeInfo); // 좋아요 수락 처리

        if (result > 0) {

            // 채팅방 생성
            Chat chat = new Chat();
            Chat chatNo = chatDao.createChatRoom(chat);
            // chat_member 테이블 추가
            try{
                // 좋아요 테이블에서 삭제

                int result1 = memberDao.deleteLikeMember(likeInfo);

                int result2 = chatDao.insertChatMember(chatNo.getClNo(),likeInfo.getRequestId());

                int result3 = chatDao.insertChatMember(chatNo.getClNo(),likeInfo.getResponseId());

                if (result1 != 1 || result2 != 1 || result3 != 1) {
                    throw new SparkException(SparkErrorCode.SPARK_999);
                }
            }catch(Exception e){
                throw new SparkException(SparkErrorCode.SPARK_999);
            }
            return result;
        } else {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
    }

    @Override
    public Integer likeNo(LikeRequest likeInfo) {

        int result = memberDao.likeNo(likeInfo); // 좋아요 거절 처리
        if (result > 0) {
            return result;
        } else {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
    }

    @Override
    public List<LikeListResponse> getLikeList(LikeListRequest likeList) {

        List<Member> result = memberDao.getLikeList(likeList);
        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }

        return result.stream()
            .map(LikeListResponse::from)
            .toList();
    }

    @Override
    public Integer interestLikeSend(InterestLikeSendRequest interestLikeSendRequest) {

        // 흥미 목록에서 삭제
        int interestRemoveResult = memberDao.interestLikeSend(interestLikeSendRequest);

        // 좋아요 테이블에 추가
        if(interestRemoveResult > 0) {
            LikeSendRequest likeSendRequest = new LikeSendRequest();
            likeSendRequest.toDto(interestLikeSendRequest);
            return memberDao.likeMember(likeSendRequest);
        }
        else {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
    }

    @Override
    public Integer interestDelete(InterestDelete interestDelete) {
        return memberDao.interestDelete(interestDelete);
    }


}
