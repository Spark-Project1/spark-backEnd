package com.sp.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.boot.dto.JwtToken;
import com.sp.boot.dto.LoginInfo;
import com.sp.boot.dto.MemberDto;
import com.sp.boot.service.MemberService;
import com.sp.boot.util.JwtProvider;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
	

	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	
	// 로그인
	
	@PostMapping("/login")
	public LoginInfo MemberLogin(MemberDto m) {
		m.setMemId(m.getMemId());
		m.setMemPwd(m.getMemPwd());
		
		
		
	    // 1. 유저 확인
	    MemberDto memberDto = memberService.login(m);

	    // 2. 토큰 생성
	    String accessToken = jwtProvider.createToken(m.getMemId());
	    //String refreshToken = jwtProvider.createRefreshToken(); // 필요하면
	    
	    
	    // 리프레시 토큰 db에저장(예정)
	    

	    JwtToken token = JwtToken.builder()
	        .grantType("Bearer")
	        .accessToken(accessToken)
	        //.refreshToken(refreshToken)
	        .build();

	    // 3. 토큰 + 사용자 정보 반환
	    return new LoginInfo(token, memberDto);
	}
	
    @PostMapping("/refresh")
    public JwtToken reissue(@RequestHeader("Authorization") String refreshTokenHeader) { // 헤더에있는 Authorization 값 받아옴

        String refreshToken = refreshTokenHeader.replace("Bearer ", ""); // Bearer를 제거해 token만 추출

        if (!jwtProvider.validateToken(refreshToken)) { // 유효성 검사
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        String userId = jwtProvider.getUserId(refreshToken);
        String AccessToken = jwtProvider.createToken(userId);
        String newRefreshToken = jwtProvider.createRefreshToken(); // 새 refresh 발급

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(AccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
	
	
	

}
