package com.spark.member.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spark.member.dto.HiddenProfileDto;
import com.spark.member.dto.InterestMemDto;
import com.spark.member.dto.MemberDto;
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


    public Optional<MemberDto> login(MemberDto m) {
        return Optional.ofNullable(memberMapper.loginMember(m));
    }

    public MemberDto loginUserInfo(String memId) {
        return memberMapper.loginUserInfo(memId);
    }

    public Optional<MemberDto> findById(MemberDto memId) {
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

    public List<MemberDto> recommendList(MemberDto memberDto) {
        return memberMapper.recommendList(memberDto);
    }

    public int signUp(MemberDto m) {
        return memberMapper.signUp(m);
    }

    public int insertInfo(MemberDto m) {
        return memberMapper.insertInfo(m);
    }

    public int recommendDelete(HiddenProfileDto hiddenProfileDto) {
        return memberMapper.recommendDelete(hiddenProfileDto);
    }

    public LikeDto likeMemberCheck(LikeDto likeData) {
        return memberMapper.likeMemberCheck(likeData);
    }

    public int likeMember(LikeDto likeSend) {
        return memberMapper.likeMember(likeSend);
    }

    public int duplicateCheck(MemberDto memberDto) {
        return memberMapper.duplicateCheck(memberDto);
    }

    public int interestMemCheck(InterestMemDto interestMemDto) {
        return memberMapper.interestMemCheck(interestMemDto);
    }

    public int interestMem(InterestMemDto interestMemDto) {
        return memberMapper.interestMem(interestMemDto);
    }

    public Optional<MemberDto> detailInfo(MemberDto memberDto) {
        return Optional.ofNullable(memberMapper.detailInfo(memberDto));
    }


    public List<MemberDto> likeList(MemberDto memberDto) {
        return memberMapper.likeList(memberDto);
    }

    public List<MemberDto> interestList(MemberDto memberDto) {
        return memberMapper.interestList(memberDto);
    }

    public int likeYes(LikeDto likeDto) {
        return memberMapper.likeYes(likeDto);
    }

    public int likeNo(LikeDto likeDto) {
        return memberMapper.likeNo(likeDto);
    }
}
