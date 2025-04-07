package com.sp.boot.service;

import org.springframework.stereotype.Service;

import com.sp.boot.dto.MemberDto;


public interface MemberService {
	
	// 멤버 로그인
	MemberDto login(MemberDto m);

	// 멤버 목록 불러오기
	MemberDto loginUserInfo(String userId);

	// 아이디로 유저 정보 찾기
	MemberDto findById(String userId);
	
	

}
