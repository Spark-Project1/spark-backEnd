package com.sp.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.boot.dto.JwtToken;
import com.sp.boot.dto.LoginInfo;
import com.sp.boot.dto.LoginResult;
import com.sp.boot.dto.LogoutResult;
import com.sp.boot.dto.MemberDto;
import com.sp.boot.dto.TokenResult;
import com.sp.boot.service.MemberService;
import com.sp.boot.util.JwtProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
	

	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	
	// 로그인
	
	@PostMapping("/login")
	public LoginInfo MemberLogin(@RequestBody MemberDto m,HttpServletResponse response) {		
			LoginResult result = memberService.login(m);
			response.addCookie(result.getRefreshCookie());
	    	return new LoginInfo(result.getToken(),result.getMemberDto());

	}
	
    @PostMapping("/refresh")
    public JwtToken refreshToken(@CookieValue("refreshToken") String refreshTokenHeader,HttpServletResponse response) { // 헤더에있는 Authorization 값 받아옴
    	TokenResult result = memberService.insertRefreshToken(refreshTokenHeader);
        response.addCookie(result.getRefreshCookie());
        return result.getJwtToken();
    }
    
    
    @GetMapping("/validate")
    public Map<String,Object> validate(@RequestHeader("Authorization") String authHeader) {
    	return memberService.loginUserInfo(authHeader);
    }
    
    
    
    @PostMapping("/sms")
    public String sms(@RequestBody String phone) {
    	return memberService.smsCode(phone);
    }
    
    
    @PostMapping("/logout")
    public boolean logout(@CookieValue("refreshToken") String refreshTokenHeader, HttpServletResponse response) {
    	
    	 LogoutResult result = memberService.deleteToken(refreshTokenHeader);

    	    if (result.isStatus() && result.getCookie() != null) {
    	        response.addCookie(result.getCookie());
    	    }

    	    return result.isStatus();

    }
    
    
    @GetMapping("/recommend")
    public List<MemberDto> recommendList(MemberDto m) {
    	return memberService.recommendList(m);
    }
    
    
    @PostMapping("signup")
    public int signup(@RequestBody MemberDto m) {
    	
    	return memberService.signUp(m);
    	
    }
    
    
    

	

}
