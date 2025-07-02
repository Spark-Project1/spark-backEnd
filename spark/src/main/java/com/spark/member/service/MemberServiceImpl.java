package com.spark.member.service;

import java.time.Year;
import java.util.*;

import com.spark.member.dto.*;
import com.spark.member.dto.request.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.base.util.JwtProvider;
import com.spark.member.dto.response.TokenResponse;
import com.spark.member.dto.response.ValidResponse;
import com.spark.member.repository.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bcryptPwdEncoder;
    private final FileUtil fileUtil;
    private final MemberValidator memberValidtor;
    private final TokenResponse tokenResponse;
    private final MemberPreprocessor memberPreprocessor;

    @Override
    public LoginResult login(LoginRequest m) {

        // 1. 유저 확인
        Member memberDto = memberDao.login(m).orElseThrow(() -> new CustomException("회원정보가 없습니다.", 400));

        memberValidtor.validationPassword(m.getMemPwd(), memberDto.getMemPwd()); // 멤버 비밀번호 유효성 검사
        // 2. 토큰 생성

        return tokenResponse.CreateToken(memberDto); // 토큰 생성 클래스 실행

    }

    @Override
    public ValidResponse loginUserInfo(TokenRequest authHeader) {
        String validateToken = authHeader.getTokenRequest().replace("Bearer ", ""); // 토큰만 뺴내어
        memberValidtor.validToken(validateToken); // 토큰 유효성 검사

        String token = jwtProvider.getUserId(validateToken);
        Member member = memberDao.loginUserInfo(token);
        MemberDto result = MemberDto.builder()
            .memId(member.getMemId())
            .memName(member.getMemName())
            .gender(member.getGender())
            .nickName(member.getNickName())
            .birthDate(member.getBirthDate())
            .location(member.getLocation())
            .memInfo(member.getMemInfo())
            .occupation(member.getOccupation())
            .education(member.getEducation())
            .mbti(member.getMbti())
            .tall(member.getTall())
            .religion(member.getReligion())
            .smock(member.getSmock())
            .status(member.getStatus())
            .registDate(member.getRegistDate())
            .cookie(member.getCookie())
            .interest(member.getInterest())
            .tendencies(member.getTendencies())
            .character(member.getCharacter())
            .proFile(member.getProFile())
            .build();
        return ValidResponse.available(result);

    }

    @Override
    public Member findById(String userId) {
        return memberDao.findById(userId)
            .orElseThrow(() -> new CustomException("해당 사용자가 존재하지 않습니다.", 403));

    }

    @Override
    public TokenResult insertRefreshToken(TokenRequest refreshTokenHeader) {

        String refreshToken = refreshTokenHeader.getTokenRequest().replace("Bearer ", "");

        memberValidtor.validToken(refreshToken); // 리프레시 토큰 유효성검사 진행

        return tokenResponse.insertRefreshToken(refreshToken);

    }

    @Override
    public LogoutResult deleteToken(TokenRequest refreshTokenHeader) {

        String token = refreshTokenHeader.getTokenRequest().replace("Bearer ", ""); // Bearer를 제거해 token만 추출

        memberValidtor.validToken(token); // 토큰 유효성 검사

        return tokenResponse.deleteToken(token);
    }


    @Override
    public String smsCode(PhoneRequest phone) {

        // 가입된 사용자인지 먼저 검사
        Optional<Member> optional = memberDao.findById(phone.getPhone());
        if (optional.isPresent()) {
            throw new CustomException("현재 가입된 사용자입니다.", 409);
        }
        return memberPreprocessor.coolSms(phone);

    }

    @Override
    public List<MemberDto> recommendList(RecommendRequest m) {

        Map<String, Object> map = new HashMap<>();
        map.put("memId", m.getMemId());
        map.put("interest", m.getInterest().split(","));
        map.put("character", m.getCharacter().split(","));
        map.put("tendencies", m.getTendencies().split(","));
        map.put("gender", String.valueOf(m.getGender()));

        List<Member> list = memberDao.recommendList(map);

        if (list == null) {
            throw new CustomException("추천 리스트 불러오기에 실패하였습니다.", 500);
        }


        Collections.shuffle(list);

        List<Member> result = list.subList(0, Math.min(50, list.size()));


        // 현재 년도
        int currentYear = Year.now().getValue();

        List<MemberDto> recommendMemberList = result.stream().map(member ->{
            int birthYear = member.getBirthDate().toLocalDate().getYear();
            int age = currentYear - birthYear;

            return MemberDto.builder()
                .memId(member.getMemId())
                .memName(member.getMemName())
                .gender(member.getGender())
                .nickName(member.getNickName())
                .birthDate(member.getBirthDate())
                .age(age)
                .location(member.getLocation())
                .memInfo(member.getMemInfo())
                .occupation(member.getOccupation())
                .education(member.getEducation())
                .mbti(member.getMbti())
                .tall(member.getTall())
                .religion(member.getReligion())
                .smock(member.getSmock())
                .status(member.getStatus())
                .registDate(member.getRegistDate())
                .cookie(member.getCookie())
                .interest(member.getInterest())
                .tendencies(member.getTendencies())
                .character(member.getCharacter())
                .proFile(member.getProFile())
                .build();

        }).toList();


        return recommendMemberList;
    }

    @Override
    public int signUp(SignUpRequest m) {

        // 비밀번호 암호화
        m.setMemPwd(bcryptPwdEncoder.encode(m.getMemPwd()));
        int memberDto = memberDao.signUp(m);
        if (memberDto == 0) {
            throw new CustomException("회원 가입에 실패하였습니다", 500);
        }


        return memberDto;
    }

    @Override
    public MemberDto insertInfo(InsertMemberInfoRequest m, MultipartFile uploadFile) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인 중인 회원의 아이디값 불러오기


        Member member = memberPreprocessor.preprocess(m, uploadFile); // 생년월일 변환 및 파일정보 입력
        memberPreprocessor.memberTallDB(member); // 멤버 키 설정
        memberPreprocessor.memberSmockDB(member); // 멤버 흡연 설정
        member.setMemId(userId);


        int result = memberDao.insertInfo(member);
        if (result == 0) {
            throw new CustomException("회원 정보 추가에 실패하였습니다.", 500);
        }

        LoginRequest mem = new LoginRequest();
        mem.setMemId(userId);

        Member memInfo = memberDao.login(mem).orElseThrow(() -> new CustomException("회원 정보 불러오기가 실패하였습니다.", 500));

        MemberDto memberDto = MemberDto.builder()
            .memId(memInfo.getMemId())
            .memName(memInfo.getMemName())
            .gender(memInfo.getGender())
            .nickName(memInfo.getNickName())
            .birthDate(memInfo.getBirthDate())
            .location(memInfo.getLocation())
            .memInfo(memInfo.getMemInfo())
            .occupation(memInfo.getOccupation())
            .education(memInfo.getEducation())
            .mbti(memInfo.getMbti())
            .tall(memInfo.getTall())
            .religion(memInfo.getReligion())
            .smock(memInfo.getSmock())
            .status(memInfo.getStatus())
            .registDate(memInfo.getRegistDate())
            .cookie(memInfo.getCookie())
            .interest(memInfo.getInterest())
            .tendencies(memInfo.getTendencies())
            .character(memInfo.getCharacter())
            .proFile(memInfo.getProFile())
            .build();


        return memberDto;
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
    public boolean duplicateCheck(String nickName) {

        try {
            int result = memberDao.duplicateCheck(nickName);
            return result <= 0;
        } catch (Exception e) {
            throw new CustomException("닉네임 중복 검사에 실패했습니다.", 500);
        }

    }

    @Override
    public int interestMem(InterestMemberAddRequest interestMember) {

        int check = memberDao.interestMemCheck(interestMember); // 이미 등록한 관심회원인지 확인
        if (check > 0) {
            throw new CustomException("이미 관심이 등록된 회원입니다.", 409);
        }

        int result = memberDao.interestMem(interestMember);
        if (result == 0) {
            throw new CustomException("관심 회원 등록에 실패하였습니다.", 500);
        }
        return result;
    }

    @Override
    public MemberDto detailInfo(Member member) {
        // 만약 내가 좋아요를 누른 상태방의 상세정보를 확인할경우
        Member m = memberDao.detailInfo(member.getMemId()).orElseThrow(() -> new CustomException("해당 회원이 존재하지 않습니다.", 400));

        LikeSendRequest list = new LikeSendRequest();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        list.setRequestId(member.getMemId());
        list.setResponseId(userId);
        LikeDto ld = memberDao.likeMemberCheck(list);
        MemberDto result = MemberDto.builder()
            .memId(m.getMemId())
            .memName(m.getMemName())
            .gender(m.getGender())
            .nickName(m.getNickName())
            .birthDate(m.getBirthDate())
            .location(m.getLocation())
            .memInfo(m.getMemInfo())
            .occupation(m.getOccupation())
            .education(m.getEducation())
            .mbti(m.getMbti())
            .tall(m.getTall())
            .religion(m.getReligion())
            .smock(m.getSmock())
            .status(m.getStatus())
            .registDate(m.getRegistDate())
            .interest(m.getInterest())
            .tendencies(m.getTendencies())
            .character(m.getCharacter())
            .proFile(m.getProFile())
            .build();

        memberPreprocessor.memberTallFront(result);
        memberPreprocessor.memberSmockFront(result);
        int currentYear = Year.now().getValue();
        int birthYear = result.getBirthDate().toLocalDate().getYear();
        result.setAge(currentYear - birthYear);

        if (ld != null) {
            result.setLikeStatus("Y");
        } else {
            result.setLikeStatus("N");
        }


        return result;
    }

    @Override
    public List<MemberDto> likeList(likeListRequest likeList) {

        Member likeListData = likeList.toEntity();

        List<Member> result = memberDao.likeList(likeListData);

        // 현재 년도
        int currentYear = Year.now().getValue();

        List<MemberDto> likeMemList = result.stream().map(member ->{
            int birthYear = member.getBirthDate().toLocalDate().getYear();
            int age = currentYear - birthYear;
            return MemberDto.builder()
                .memId(member.getMemId())
                .nickName(member.getNickName())
                .birthDate(member.getBirthDate())
                .age(age)
                .proFile(member.getProFile())
                .build();
        }).toList();

        return null;
    }


}
