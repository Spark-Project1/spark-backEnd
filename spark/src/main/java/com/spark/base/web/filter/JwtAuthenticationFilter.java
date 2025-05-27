package com.spark.base.web.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spark.base.util.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{ // once를 사용해서 딱 요청마다 한번만 실행함
	
	   private final JwtProvider jwtProvider;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	        String token = jwtProvider.resolveToken(request); // 요청 헤더에서 Authorization 값을 꺼냄
	        
	        if (token != null && jwtProvider.validateToken(token)) {
	            String userId = jwtProvider.getUserId(token);	            
	            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, null); // db에서 사용자 정보를 찾아 인증객체 생성
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
	    }
	
	
	

}
