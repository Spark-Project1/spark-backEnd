package com.sp.boot.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sp.boot.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	 MemberDto loginMember(MemberDto m);
	 
	 MemberDto loginUserInfo(String userId);
	 
	 MemberDto findById(String userId);
	 
	 int insertRefreshToken(Map<String,Object> map);
	 
	 String checkRefreshToken(Map<String, Object> map);
	 
	 int deleteRefreshToken(Map<String, Object> map);

}
