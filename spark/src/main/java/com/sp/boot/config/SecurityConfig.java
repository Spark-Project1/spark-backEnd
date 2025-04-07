package com.sp.boot.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sp.boot.filter.JwtAuthenticationFilter;
import com.sp.boot.service.MemberService;
import com.sp.boot.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 세션을 사용하지않기때문에 csrf를 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않겠다는 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/signup").permitAll() // 로그인 없이 접근가능한 경로
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtProvider), // 시큐라티 체인에 등록
                UsernamePasswordAuthenticationFilter.class // 기본 로그인 필터보다 앞에서 실행되도록 설정
            );

        return http.build();
    }
   
   
   
   
   
   
   @Configuration
   public class WebConfig implements WebMvcConfigurer {

       @Override
       public void addCorsMappings(CorsRegistry registry) {
           // 모든 엔드포인트에 대해 CORS 적용
           registry.addMapping("/**") // 모든 경로로 설정
               .allowedOrigins("http://localhost:3000")  // React 앱의 주소
               .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
               .allowedHeaders("*")  // 모든 헤더 허용
               .allowCredentials(true);  // 자격 증명 (예: 쿠키) 허용
       }
   }
   
   
   
   
   
   
   
   
   
   
   
}
