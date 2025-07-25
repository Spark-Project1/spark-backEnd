package com.spark.member.repository;

import java.util.List;
import java.util.Map;

import com.spark.member.dto.HiddenProfileDto;
import com.spark.member.dto.InterestMemDto;
import com.spark.member.dto.MemberDto;
import com.spark.member.model.Member;
import com.spark.member.dto.request.*;
import org.apache.ibatis.annotations.Mapper;

import com.spark.member.dto.LikeDto;

@Mapper
public interface MemberMapper {

    MemberDto loginMember(MemberDto m);

    MemberDto loginUserInfo(String userId);

    MemberDto findById(MemberDto userId);

    int insertRefreshToken(Map<String, Object> map);

    int checkRefreshToken(Map<String, Object> map);

    int updateRefreshToken(Map<String, Object> map);

    int deleteToken(String userId);

    List<MemberDto> recommendList(MemberDto memberDto);

    int signUp(MemberDto m);

    int insertInfo(MemberDto m);

    int recommendDelete(HiddenProfileDto hiddenProfileDto);

    LikeDto likeMemberCheck(LikeDto likeData);

    int likeMember(LikeDto likeSend);

    int duplicateCheck(MemberDto memberDto);

    int interestMemCheck(InterestMemDto interestMemDto);

    int interestMem(InterestMemDto interestMemDto);

    MemberDto detailInfo(MemberDto memberDto);

    List<MemberDto> likeList(MemberDto memberDto);

    List<MemberDto> interestList(MemberDto memberDto);

    int likeYes(LikeDto likeDto);

    int likeNo(LikeDto likeDto);

}
