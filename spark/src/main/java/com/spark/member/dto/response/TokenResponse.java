package com.spark.member.dto.response;

import java.util.HashMap;
import java.util.Map;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.member.dto.*;
import com.spark.member.model.Member;
import org.springframework.stereotype.Component;

import com.spark.base.exception.CustomException;
import com.spark.base.util.JwtProvider;
import com.spark.member.repository.MemberDao;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenResponse {
	
	private final JwtProvider jwtProvider;
	private final MemberDao memberDao;
	
	
	public LoginResult CreateToken(LoginResponse response) {
		
    	String accessToken = jwtProvider.createToken(response.getMemId()); // access 토큰 발급
    	String refreshToken = jwtProvider.createRefreshToken(response.getMemId());  // refresh 토큰 발급
    	Map<String,Object> map = new HashMap<>();
    	map.put("memId", response.getMemId());
    	map.put("refreshToken", refreshToken);
    	int result = memberDao.insertRefreshToken(map); // db에 refreshtoken 저장
    	if(result == 0) {
    		throw new SparkException(SparkErrorCode.SPARK_999);
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
    	response.setMemPwd(null); // 비밀번호는 클라이언트에 보내지 않음

    	return new LoginResult(token, response, refreshCookie);

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
        	throw new SparkException(SparkErrorCode.SPARK_999);
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
	            throw new SparkException(SparkErrorCode.SPARK_999);
	        }
	        return result2;
	    } else {
	        int insertResult = memberDao.insertRefreshToken(map);
	        if (insertResult == 0) {
	            throw new SparkException(SparkErrorCode.SPARK_999);
	        }
	        return insertResult;
	    }
		
	}
	
	
	
	public LogoutResult deleteToken(String token) {
		
		
		String userId = jwtProvider.getUserId(token); // 토큰 안에있는 userId 가져오기
        int result = memberDao.deleteToken(userId); // 토큰 db에서 제거
    	
        if(result == 0) {
        	throw new SparkException(SparkErrorCode.SPARK_999);
        }
    
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setPath("/");
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(false);
        deleteCookie.setMaxAge(0); // 0초 즉시 만료
        
        return new LogoutResult(true,deleteCookie);
        
	}
	
	
	
	
	
	

}
