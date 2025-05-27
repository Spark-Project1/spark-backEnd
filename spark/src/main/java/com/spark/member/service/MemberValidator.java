package com.spark.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.spark.base.exception.CustomException;
import com.spark.base.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {
	
	private final JwtProvider jwtProvider;
	private final BCryptPasswordEncoder bcryptPwdEncoder;
	
	
	public void validationPassword(String inputPassword,String password) {
		
		if(!bcryptPwdEncoder.matches(inputPassword, password)) {
			throw new CustomException("올비르지 않은 비밀번호 입니다.",401);
		}
		
	}
	
	
	public void validToken(String validateToken) {
		
        if (!jwtProvider.validateToken(validateToken)) { // 유효성 검사 진행
            throw new CustomException("유효하지 않은 토큰입니다.",401);
        }
		
	}
	
	

}
