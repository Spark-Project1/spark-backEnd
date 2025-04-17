package com.sp.boot.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sp.boot.dto.JwtToken;
import com.sp.boot.dto.LoginInfo;
import com.sp.boot.dto.LoginResult;
import com.sp.boot.dto.LogoutResult;
import com.sp.boot.dto.MemberDto;
import com.sp.boot.dto.TokenResult;

import jakarta.servlet.http.HttpServletResponse;


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

	
	

}
