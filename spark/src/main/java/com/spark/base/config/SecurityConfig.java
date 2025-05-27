package com.spark.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spark.base.util.JwtProvider;
import com.spark.base.web.filter.JwtAuthenticationFilter;
import com.spark.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
    private final JwtProvider jwtProvider;

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.cors(cors -> {})
            .csrf(csrf -> csrf.disable()) // 세션을 사용하지않기때문에 csrf를 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않겠다는 설정
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/login","/api/validate","/api/refresh","/api/logout","/api/sms","/api/signup","/profile/**").permitAll() // 로그인/회원가입 허용
                  .anyRequest().authenticated() // 그 외의 api는 인증이 필요
            )
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtProvider), // 시큐라티 체인에 등록
                UsernamePasswordAuthenticationFilter.class // 기본 로그인 필터보다 앞에서 실행되도록 설정
            );

        return http.build(); // 최종적으로 작성한 http 빌드후 리턴
    }
   
	@Bean
	BCryptPasswordEncoder bcryptPwdEncoder() {
		return new BCryptPasswordEncoder();
	}
   
	

   
   
   

   
   
   
   
   
   
   
   
   
   
   
}
