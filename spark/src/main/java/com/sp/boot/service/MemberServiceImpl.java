package com.sp.boot.service;

import org.springframework.stereotype.Service;

import com.sp.boot.dao.MemberDao;
import com.sp.boot.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberDao memberDao;
	
	@Override
	public MemberDto login(MemberDto m) {
		return memberDao.login(m);
	}

	@Override
	public MemberDto loginUserInfo(String userId) {
		
		return memberDao.loginUserInfo(userId);
	}

	@Override
	public MemberDto findById(String userId) {
		return null;
	}


	
	

}
