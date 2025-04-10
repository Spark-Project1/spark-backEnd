package com.sp.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sp.boot.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	 MemberDto loginMember(MemberDto m);
	 
	 MemberDto loginUserInfo(String userId);
	 
	 MemberDto findById(String userId);

}
