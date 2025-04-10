package com.sp.boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtToken {
	
	private String grantType; // JWT 인증 타입
	private String accessToken;
	
	// Bearer 인증방식을 사용

}
