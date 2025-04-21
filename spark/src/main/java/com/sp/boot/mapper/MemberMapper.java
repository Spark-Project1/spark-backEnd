package com.sp.boot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sp.boot.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	 MemberDto loginMember(MemberDto m);
	 
	 MemberDto loginUserInfo(String userId);
	 
	 MemberDto findById(String userId);
	 
	 int insertRefreshToken(Map<String,Object> map);
	 
	 int checkRefreshToken(Map<String, Object> map);
	 
	 int updateRefreshToken(Map<String, Object> map);
	 
	 int deleteToken(String userId);
	 
	 List<MemberDto> recommendList(Map<String, Object> map);

	 int signUp(MemberDto m);
	 
	 int insertInfo(MemberDto m);
	 
}
