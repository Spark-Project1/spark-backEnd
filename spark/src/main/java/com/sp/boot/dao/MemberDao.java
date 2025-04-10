package com.sp.boot.dao;

import org.springframework.stereotype.Repository;

import com.sp.boot.dto.MemberDto;
import com.sp.boot.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao {
	
	//private final SqlSessionTemplate sqlSession;
	private final MemberMapper memberMapper;

	/*
	 * public MemberDto login(MemberDto m) { return
	 * sqlSession.selectOne("memberMapper.loginMember",m); }
	 * 
	 * public MemberDto loginUserInfo(String userId) { return
	 * sqlSession.selectOne("memberMapper.loginUserInfo",userId); }
	 */
	

    public MemberDto login(MemberDto m) {
        return memberMapper.loginMember(m);
    }

    public MemberDto loginUserInfo(String memId) {
        return memberMapper.loginUserInfo(memId);
    }
	
	
	
	
	

}
