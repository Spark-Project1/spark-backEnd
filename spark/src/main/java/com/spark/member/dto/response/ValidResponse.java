package com.spark.member.dto.response;

import lombok.Getter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
@Getter
@Component
@RequiredArgsConstructor
public class ValidResponse {
	
	private  boolean valid;
	private  LoginResponse memberDto;

	public ValidResponse(boolean valid,LoginResponse member) {
		this.valid = valid;
		this.memberDto = member;
	}


	public static ValidResponse available(LoginResponse member) {
		return new ValidResponse(true,member);
	}
	
	
	
	

}
