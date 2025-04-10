package com.sp.boot.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sp.boot.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao {
	
	private final SqlSessionTemplate sqlSession;

	public MemberDto login(MemberDto m) {
		return sqlSession.selectOne("memberMapper.loginMember",m);
	}

	public MemberDto loginUserInfo(String userId) {
		return sqlSession.selectOne("memberMapper.loginUserInfo",userId);
	}

	public MemberDto findById(String userId) {
		return sqlSession.selectOne("memberMapper.findById",userId);
	}
	
	
	
	
	
	
	

}
