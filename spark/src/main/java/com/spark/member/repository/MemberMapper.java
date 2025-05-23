package com.spark.member.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.member.dto.LikeDto;
import com.spark.member.dto.MemberDto;

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
	 
	 int recommendDelete(Map<String, String> map);
	 
	 LikeDto likeMemberCheck(Map<String, String> map);
	 
	 int likeMember(Map<String, String> map);

	 int duplicateCheck(String nickName);
	 
	 int interestMemCheck(Map<String, String> map);
	 
	 int interestMem(Map<String, String> map);
	 
	 MemberDto detailInfo(String memId);
	 
}
