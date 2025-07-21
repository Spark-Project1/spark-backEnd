package com.spark.member.repository;

import java.util.List;
import java.util.Map;

import com.spark.member.model.Member;
import com.spark.member.dto.request.*;
import org.apache.ibatis.annotations.Mapper;

import com.spark.member.dto.LikeDto;

@Mapper
public interface MemberMapper {

    Member loginMember(LoginRequest m);

    Member loginUserInfo(String userId);

    Member findById(String userId);

    int insertRefreshToken(Map<String, Object> map);

    int checkRefreshToken(Map<String, Object> map);

    int updateRefreshToken(Map<String, Object> map);

    int deleteToken(String userId);

    List<Member> recommendList(Map<String, Object> map);

    int signUp(SignUpRequest m);

    int insertInfo(Member m);

    int recommendDelete(RecommendDeleteRequest recommendDelete);

    LikeDto likeMemberCheck(LikeDto likeData);

    int likeMember(LikeSendRequest likeSend);

    int duplicateCheck(String nickName);

    int interestMemCheck(InterestMemberAddRequest interestMember);

    int interestMem(InterestMemberAddRequest interestMember);

    Member detailInfo(String memId);

    List<Member> likeList(Member likeListData);

    List<Member> interestList(Member interestListData);

    int likeYes(LikeDto likeData);

    int likeNo(LikeDto likeData);

}
