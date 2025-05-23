package com.spark.member.application;

import java.util.List;
import java.util.Map;

import com.spark.member.dto.LoginResult;
import com.spark.member.dto.LogoutResult;
import com.spark.member.dto.MemberDto;
import com.spark.member.dto.TokenResult;
import org.springframework.web.multipart.MultipartFile;


public interface MemberService {
	
	// 멤버 로그인
	LoginResult login(MemberDto m);

	// 멤버 목록 불러오기
	Map<String, Object> loginUserInfo(String userId);

	// 아이디로 유저 정보 찾기
	MemberDto findById(String userId);

	TokenResult insertRefreshToken(String refreshTokenHeader);

	LogoutResult deleteToken(String userId);

	// 문자로 인증번호 발송
	String smsCode(String phone);

	List<MemberDto> recommendList(MemberDto m);

	int signUp(MemberDto m);

	MemberDto insertInfo(MemberDto m, MultipartFile uploadFile);

	int recommendDelete(Map<String,String> map);

	int likeMember(Map<String,String> map);

	boolean duplicateCheck(String nickName);

	int interestMem(Map<String, String> map);

	MemberDto detailInfo(String memId);

	
	

}
