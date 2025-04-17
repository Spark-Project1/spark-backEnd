package com.sp.boot.dto;

import java.sql.Date;

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
public class TokenResult {
	
	private JwtToken jwtToken;
    private Cookie refreshCookie;

}
