package com.spark.member.dto.response;

import org.springframework.stereotype.Component;

import com.spark.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidResponse {
	
	private  boolean valid;
	private  MemberDto member;
	
	public ValidResponse(boolean valid,MemberDto member) {
		this.valid = valid;
		this.member = member;
	}
	
	
	public static ValidResponse available(MemberDto member) {
		return new ValidResponse(true,member);
	}
	
	
	
	

}
