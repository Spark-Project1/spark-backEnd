package com.spark.member.service;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
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

        String token = validateToken.replace("Bearer ", "");

        if (!jwtProvider.validateToken(token)) { // 유효성 검사 진행
            throw new SparkException(SparkErrorCode.SPARK_104);
        }

    }


}
