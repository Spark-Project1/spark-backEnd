package com.spark.member.dto.response;

import lombok.Getter;
import org.springframework.stereotype.Component;

import com.spark.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;
@Getter
@Component
@RequiredArgsConstructor
public class ValidResponse {
	
	private  boolean valid;
	private  MemberDto memberDto;

	public ValidResponse(boolean valid,MemberDto member) {
		this.valid = valid;
		this.memberDto = member;
	}


	public static ValidResponse available(MemberDto member) {
		return new ValidResponse(true,member);
	}
	
	
	
	

}
