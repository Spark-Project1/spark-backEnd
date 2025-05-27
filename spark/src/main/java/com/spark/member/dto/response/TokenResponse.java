package com.spark.member.dto.response;

import java.util.HashMap;
import java.util.Map;

import com.spark.base.exception.CustomException;
import com.spark.base.util.JwtProvider;
import com.spark.member.dto.JwtToken;
import com.spark.member.dto.LoginResult;
import com.spark.member.dto.LogoutResult;
import com.spark.member.dto.MemberDto;
import com.spark.member.dto.TokenResult;
import com.spark.member.repository.MemberDao;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TokenResponse {
	
	private final JwtProvider jwtProvider;
	private final MemberDao memberDao;
	
	
	public LoginResult CreateToken(MemberDto m) {
		
    	String accessToken = jwtProvider.createToken(m.getMemId()); // access 토큰 발급
    	String refreshToken = jwtProvider.createRefreshToken(m.getMemId());  // refresh 토큰 발급
    	Map<String,Object> map = new HashMap<>();
    	map.put("memId", m.getMemId());
    	map.put("refreshToken", refreshToken);
    	int result = memberDao.insertRefreshToken(map); // db에 refreshtoken 저장
    	if(result == 0) {
    		throw new CustomException("리프레시 토큰 저장에 실패하였습니다.",500);
    	}
    	JwtToken token = JwtToken.builder()
    			.grantType("Bearer")
    			.accessToken(accessToken)
    			.build();
    	Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
    	refreshCookie.setHttpOnly(true); // javascript에서 접근 차단
    	refreshCookie.setPath("/"); // 어디에 쿠키를 쓸지 경로 설정
    	refreshCookie.setSecure(false); // https 환경에서만 사용가능 현재는 false로 막아둔상태 ssl적용x 
    	refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
    	m.setMemPwd(null);
    	
    	return new LoginResult(token, m, refreshCookie);

	}
	
	
	
	public TokenResult insertRefreshToken(String refreshToken) {
		
		String userId = jwtProvider.getUserId(refreshToken);
        String accessToken = jwtProvider.createToken(userId);
        String newRefreshToken = jwtProvider.createRefreshToken(userId);
		
		
        Map<String, Object> map = new HashMap<>();
        map.put("memId", userId);
        map.put("refreshToken", newRefreshToken);
        int result = checkRefreshToken(map);
        if(result == 0) {
        	throw new CustomException("리프레시 토큰 저장에 실패하였습니다",500);
        }   
        Cookie refreshCookie = null;
    	refreshCookie = new Cookie("refreshToken", newRefreshToken);
    	refreshCookie.setHttpOnly(true); // javascript에서 접근 차단
    	refreshCookie.setPath("/"); // 어디에 쿠키를 쓸지 경로 설정
    	refreshCookie.setSecure(false); // https 환경에서만 사용가능 현재는 false로 막아둔상태
    	refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

        JwtToken jwtToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();

        return new TokenResult(jwtToken,refreshCookie);
		
	}

	
	public int checkRefreshToken(Map<String,Object> map) {
		
		int result = memberDao.checkRefreshToken(map);
		
	    if (result > 0) {
	        int result2 = memberDao.updateRefreshToken(map);
	        if (result2 == 0) {
	            throw new CustomException("리프레시 토큰 업데이트에 실패했습니다.", 500);
	        }
	        return result2;
	    } else {
	        int insertResult = memberDao.insertRefreshToken(map);
	        if (insertResult == 0) {
	            throw new CustomException("리프레시 토큰 저장에 실패했습니다.", 500);
	        }
	        return insertResult;
	    }
		
	}
	
	
	
	public LogoutResult deleteToken(String token) {
		
		
		String userId = jwtProvider.getUserId(token); // 토큰 안에있는 userId 가져오기
        int result = memberDao.deleteToken(userId); // 토큰 db에서 제거
    	
        if(result == 0) {
        	throw new CustomException("토큰 삭제에 실패하였습니다",500);
        }
    
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(false);
        deleteCookie.setMaxAge(0); // 0초 즉시 만료
        
        return new LogoutResult(true,deleteCookie);
        
	}
	
	
	
	
	
	

}
