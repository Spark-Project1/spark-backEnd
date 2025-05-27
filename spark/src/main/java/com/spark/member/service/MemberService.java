package com.spark.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.spark.member.dto.LoginResult;
import com.spark.member.dto.LogoutResult;
import com.spark.member.dto.MemberDto;
import com.spark.member.dto.TokenResult;
import com.spark.member.dto.request.LoginRequest;
import com.spark.member.dto.request.PhoneRequest;
import com.spark.member.dto.request.TokenRequest;
import com.spark.member.dto.response.ValidResponse;


public interface MemberService {
	
	// 멤버 로그인
	LoginResult login(LoginRequest m);

	// 멤버 목록 불러오기
	ValidResponse loginUserInfo(TokenRequest authHeader);

	// 아이디로 유저 정보 찾기
	MemberDto findById(String userId);

	TokenResult insertRefreshToken(TokenRequest refreshTokenHeader);

	LogoutResult deleteToken(TokenRequest refreshTokenHeader);

	// 문자로 인증번호 발송
	String smsCode(PhoneRequest phone);

	List<MemberDto> recommendList(MemberDto m);

	int signUp(MemberDto m);

	MemberDto insertInfo(MemberDto m, MultipartFile uploadFile);

	int recommendDelete(Map<String,String> map);

	int likeMember(Map<String,String> map);

	boolean duplicateCheck(String nickName);

	int interestMem(Map<String, String> map);

	MemberDto detailInfo(String memId);

	
	

}
