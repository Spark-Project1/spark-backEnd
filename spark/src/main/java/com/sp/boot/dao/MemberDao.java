package com.sp.boot.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sp.boot.dto.FeedDto;
import com.sp.boot.dto.LikeDto;
import com.sp.boot.dto.MemberDto;
import com.sp.boot.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao {
	
	//private final SqlSessionTemplate sqlSession;
	private final MemberMapper memberMapper;


    public MemberDto login(MemberDto m) {
        return memberMapper.loginMember(m);   
    }

    public MemberDto loginUserInfo(String memId) {
        return memberMapper.loginUserInfo(memId);
    }

	public MemberDto findById(String userId) {
		return memberMapper.findById(userId);
	}

	public int insertRefreshToken(Map<String, Object> map) {
		return memberMapper.insertRefreshToken(map);
	}

	public int checkRefreshToken(Map<String, Object> map) {
		return memberMapper.checkRefreshToken(map);
	}

	public int updateRefreshToken(Map<String, Object> map) {
		return memberMapper.updateRefreshToken(map);
	}

	public int deleteToken(String userId) {
		return memberMapper.deleteToken(userId);
	}

	public List<MemberDto> recommendList(Map<String, Object> map) {
		return memberMapper.recommendList(map);
	}

	public int signUp(MemberDto m) {
		return memberMapper.signUp(m);
	}

	public int insertInfo(MemberDto m) {
		return memberMapper.insertInfo(m);
	}

	public int recommendDelete(Map<String, String> map) {
		return memberMapper.recommendDelete(map);
	}

	public LikeDto likeMemberCheck(Map<String, String> map) {
		return memberMapper.likeMemberCheck(map);
	}

	public int likeMember(Map<String, String> map) {
		return memberMapper.likeMember(map);
	}

	public int duplicateCheck(String nickName) {
		return memberMapper.duplicateCheck(nickName);
	}


	
	
	
	
	

}
