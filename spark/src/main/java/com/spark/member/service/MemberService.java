package com.spark.member.service;

import java.util.List;

import com.spark.member.dto.*;
import com.spark.member.dto.request.*;
import com.spark.member.dto.response.*;
import com.spark.member.model.Member;
import org.springframework.web.multipart.MultipartFile;


public interface MemberService {
	
	// 멤버 로그인
	LoginResult login(LoginRequest m);

	// 멤버 목록 불러오기
	ValidResponse loginUserInfo(TokenRequest authHeader);

	// 아이디로 유저 정보 찾기
    Member findById(String userId);

	TokenResult insertRefreshToken(TokenRequest refreshTokenHeader);

	LogoutResult deleteToken(TokenRequest refreshTokenHeader);

	// 문자로 인증번호 발송
	String smsCode(PhoneRequest phone);

	List<RecommendResponse> recommendList(RecommendRequest m);

	int signUp(SignUpRequest m);

    LoginResponse insertInfo(InsertMemberInfoRequest m, MultipartFile uploadFile);

	int recommendDelete(RecommendDeleteRequest recommendDelete);

	int likeMember(LikeSendRequest likeSend);

	boolean duplicateCheck(DuplicateCheckRequest nickName);

	int interestMem(InterestMemberAddRequest interestMember);

    DetailMemberInfoResponse detailInfo(DetailMemberInfoRequest detailMemberInfo);


    List<LikeListResponse> likeList(LikeListRequest likeList);

    List<InterestListResponse> interestList(InterestListRequest interestList);

    Integer likeYes(LikeRequest likeInfo);

    Integer likeNo(LikeRequest likeInfo);
}
