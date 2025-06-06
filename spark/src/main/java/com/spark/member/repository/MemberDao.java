package com.spark.member.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spark.member.dto.Member;
import com.spark.member.dto.request.*;
import org.springframework.stereotype.Repository;

import com.spark.member.dto.LikeDto;
import com.spark.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    //private final SqlSessionTemplate sqlSession;
    private final MemberMapper memberMapper;


    public Optional<Member> login(LoginRequest m) {
        return Optional.ofNullable(memberMapper.loginMember(m));
    }

    public Member loginUserInfo(String memId) {
        return memberMapper.loginUserInfo(memId);
    }

    public Optional<Member> findById(String userId) {
        return Optional.ofNullable(memberMapper.findById(userId));
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

    public List<Member> recommendList(Map<String, Object> map) {
        return memberMapper.recommendList(map);
    }

    public int signUp(SignUpRequest m) {
        return memberMapper.signUp(m);
    }

    public int insertInfo(Member m) {
        return memberMapper.insertInfo(m);
    }

    public int recommendDelete(RecommendDeleteRequest recommendDelete) {
        return memberMapper.recommendDelete(recommendDelete);
    }

    public LikeDto likeMemberCheck(LikeSendRequest likeSend) {
        return memberMapper.likeMemberCheck(likeSend);
    }

    public int likeMember(LikeSendRequest likeSend) {
        return memberMapper.likeMember(likeSend);
    }

    public int duplicateCheck(String nickName) {
        return memberMapper.duplicateCheck(nickName);
    }

    public int interestMemCheck(InterestMemberAddRequest interestMember) {
        return memberMapper.interestMemCheck(interestMember);
    }

    public int interestMem(InterestMemberAddRequest interestMember) {
        return memberMapper.interestMem(interestMember);
    }

    public Optional<Member> detailInfo(String memId) {
        return Optional.ofNullable(memberMapper.detailInfo(memId));
    }


}
