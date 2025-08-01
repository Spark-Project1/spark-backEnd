package com.spark.member.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spark.member.model.Member;
import com.spark.member.dto.request.*;
import org.springframework.stereotype.Repository;

import com.spark.member.dto.LikeDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    //private final SqlSessionTemplate sqlSession;
    private final MemberMapper memberMapper;


    public Optional<Member> login(String m) {
        return Optional.ofNullable(memberMapper.loginMember(m));
    }

    public Member loginUserInfo(String memId) {
        return memberMapper.loginUserInfo(memId);
    }

    public Optional<Member> findById(String memId) {
        return Optional.ofNullable(memberMapper.findById(memId));
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

    public List<Member> recommendList(Member member) {
        return memberMapper.recommendList(member);
    }

    public int signUp(Member member) {
        return memberMapper.signUp(member);
    }

    public int insertInfo(Member member) {
        return memberMapper.insertInfo(member);
    }

    public int recommendDelete(RecommendDeleteRequest recommendDelete) {
        return memberMapper.recommendDelete(recommendDelete);
    }

    public LikeDto likeMemberCheck(LikeSendRequest likeSendRequest) {
        return memberMapper.likeMemberCheck(likeSendRequest);
    }

    public int likeMember(LikeSendRequest likeSendRequest) {
        return memberMapper.likeMember(likeSendRequest);
    }

    public int duplicateCheck(DuplicateCheckRequest duplicateCheckRequest) {
        return memberMapper.duplicateCheck(duplicateCheckRequest);
    }

    public int interestMemCheck(InterestMemberAddRequest interestMemberAddRequest) {
        return memberMapper.interestMemCheck(interestMemberAddRequest);
    }

    public int interestMem(InterestMemberAddRequest interestMemberAddRequest) {
        return memberMapper.interestMem(interestMemberAddRequest);
    }

    public Optional<Member> detailInfo(DetailMemberInfoRequest detailMemberInfo) {
        return Optional.ofNullable(memberMapper.detailInfo(detailMemberInfo));
    }


    public List<Member> likeList(LikeListRequest likeList) {
        return memberMapper.likeList(likeList);
    }

    public List<Member> interestList(InterestListRequest interestList) {
        return memberMapper.interestList(interestList);
    }

    public int likeYes(LikeRequest likeInfo) {
        return memberMapper.likeYes(likeInfo);
    }

    public int likeNo(LikeRequest likeInfo) {
        return memberMapper.likeNo(likeInfo);
    }
}
