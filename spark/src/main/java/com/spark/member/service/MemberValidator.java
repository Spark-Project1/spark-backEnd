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

    public void validToken(String validateToken) {

        if (!jwtProvider.validateToken(validateToken)) { // 유효성 검사 진행
            throw new CustomException("유효하지 않은 토큰입니다.", 401);
        }

    }


}
