package com.sp.boot.service;

import java.util.Map;

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
		return memberDao.findById(userId);
	}

	@Override
	public int insertRefreshToken(Map<String, Object> map) {
		
		int result = memberDao.checkRefreshToken(map);
		
		if(result > 0) {
			int result2 = memberDao.updateRefreshToken(map);

		}else {
			return memberDao.insertRefreshToken(map);
		}
		return 0;
	}


	
	

}
