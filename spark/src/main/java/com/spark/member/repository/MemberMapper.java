package com.spark.member.repository;

import java.util.List;
import java.util.Map;

import com.spark.member.model.Member;
import com.spark.member.dto.request.*;
import org.apache.ibatis.annotations.Mapper;

import com.spark.member.dto.LikeDto;

@Mapper
public interface MemberMapper {

    Member loginMember(String m);

    Member loginUserInfo(String userId);

    Member findById(String userId);

    int insertRefreshToken(Map<String, Object> map);

    int checkRefreshToken(Map<String, Object> map);

    int updateRefreshToken(Map<String, Object> map);

    int deleteToken(String userId);

    List<Member> recommendList(Member member);

    int signUp(Member member);

    int insertInfo(Member member);

    int recommendDelete(RecommendDeleteRequest recommendDelete);

    LikeDto likeMemberCheck(LikeSendRequest likeSendRequest);

    int likeMember(LikeSendRequest likeSendRequest);

    int duplicateCheck(DuplicateCheckRequest duplicateCheckRequest);

    int interestMemCheck(InterestMemberAddRequest interestMemberAddRequest);

    int interestMem(InterestMemberAddRequest interestMemberAddRequest);

    Member detailInfo(DetailMemberInfoRequest detailMemberInfo);

    List<Member> likeList(LikeListRequest likeList);

    List<Member> interestList(InterestListRequest interestList);

    int likeYes(LikeRequest likeInfo);

    int likeNo(LikeRequest likeInfo);

}
