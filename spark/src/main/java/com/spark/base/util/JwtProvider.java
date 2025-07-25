package com.spark.base.util;

import java.util.Date;

import com.spark.base.config.SparkKeyConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spark.member.dto.MemberDto;
import com.spark.member.service.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {


    // JWT 관련 기능을 제공하는 클래스
    private final SparkKeyConfig sparkKeyConfig;
    private final long tokenValidTime = 1000 * 60 * 30; // 토큰 유효 시간  1000 * 60 * 60; (1시간 = 3600000ms)
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // 5시간


    // access 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);// JWT의 payload 부분 sub에 아이디를 담음
        Date now = new Date(); // 현재 시간 저장
        return Jwts.builder()
            .setClaims(claims) // 위에만든 claims정보를 집어넣음
            .setIssuedAt(now)  // 토큰 발급 시간 저장
            .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료 시간 설정 (현재시간+유효시간)
            .signWith(Keys.hmacShaKeyFor(sparkKeyConfig.getSecretKey().getBytes()), SignatureAlgorithm.HS256) // 알고리즘으로 암호화
            .compact(); // 위 정보로 jwt 문자열 생성
    }


    // refresh 토큰 생성
    public String createRefreshToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(Keys.hmacShaKeyFor(sparkKeyConfig.getSecretKey().getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }


    // 토큰 유효성 검증
    // 요청 시 토큰이 진짜인지, 시간이 만료되지않았는지 검증
    // 필터를 거쳐서 실행됨
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(sparkKeyConfig.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token); // 토큰 파싱
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(sparkKeyConfig.getSecretKey().getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // 헤더에서 토큰 꺼내기
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }


}
