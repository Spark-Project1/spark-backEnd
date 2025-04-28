package com.sp.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	// 리프레시 토큰 발급
    @PostMapping("/refresh")
    public JwtToken refreshToken(@CookieValue("refreshToken") String refreshTokenHeader,HttpServletResponse response) { // 헤더에있는 Authorization 값 받아옴
    	TokenResult result = memberService.insertRefreshToken(refreshTokenHeader);
        response.addCookie(result.getRefreshCookie());
        return result.getJwtToken();
    }
    
    // 유효성 검사
    @GetMapping("/validate")
    public Map<String,Object> validate(@RequestHeader("Authorization") String authHeader) {
    	return memberService.loginUserInfo(authHeader);
    }
    
    
    // 쿨sms
    @PostMapping("/sms")
    public String sms(@RequestBody String phone) {
    	return memberService.smsCode(phone);
    }
    
    // 로그아웃
    @PostMapping("/logout")
    public boolean logout(@CookieValue("refreshToken") String refreshTokenHeader, HttpServletResponse response) {
    	
    	 LogoutResult result = memberService.deleteToken(refreshTokenHeader);

    	    if (result.isStatus() && result.getCookie() != null) {
    	        response.addCookie(result.getCookie());
    	    }

    	    return result.isStatus();

    }
    
    // 메인화면 추천목록
    @PostMapping("/recommend")
    public List<MemberDto> recommendList(@RequestBody MemberDto m) {
    	return memberService.recommendList(m);
    }
    
    // 회원가입
    @PostMapping("/signup")
    public int signup(@RequestBody MemberDto m) {
    	return memberService.signUp(m);
    	
    }
    
    // 회원 정보입력
    @PatchMapping("/insertInfo")
    public MemberDto insertInfo(@ModelAttribute MemberDto m, @RequestParam("uploadFile") MultipartFile uploadFile) {  	
    	return memberService.insertInfo(m,uploadFile);
    }
    
    // 추천목록 삭제
    @PostMapping("/recommendDelete")
    public int recommendDelete(@RequestBody Map<String,String> map) {
    	return memberService.recommendDelete(map);
    }

    // 좋아요 신청
	@PostMapping("/like")
	public int likeMember(@RequestBody Map<String,String> map) {		
		System.out.println(map);
		return memberService.likeMember(map);
	}
    
	// 닉네임 중복검사
	
	@GetMapping("/duplicateCheck")
	public boolean duplicateCheck(@RequestParam String nickName) {
		return memberService.duplicateCheck(nickName);
	}
	
	
	// 관심목록 추가
	@PostMapping("/interestMem")
	public int interestMem(@RequestBody Map<String,String> map) {
		return memberService.interestMem(map);
	}
	
	// 상대방 상세정보 불러오기
	@GetMapping("/DetailInfo")
	public MemberDto detailInfo(@RequestParam String memId) {
		return memberService.detailInfo(memId);
	}

    

}
