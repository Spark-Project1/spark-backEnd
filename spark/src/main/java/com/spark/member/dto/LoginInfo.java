package com.spark.member.dto;

import com.spark.member.dto.response.LoginResponse;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LoginInfo {
	
	private JwtToken token;
	private LoginResponse memberDto;

}
