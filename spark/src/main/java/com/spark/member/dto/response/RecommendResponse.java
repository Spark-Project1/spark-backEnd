package com.spark.member.dto.response;

import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.dto.MemberDto;
import lombok.*;

import java.sql.Date;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecommendResponse {

    private String memId;
    private String memName;
    private String gender;
    private String nickName;
    private Date birthDate;
    private int age;
    private String location;
    private String memInfo;
    private String occupation;
    private String education;
    private String mbti;
    private Tall tall;
    private String religion;
    private Smock smock;
    private String status;
    private Date registDate;
    private int cookie;
    private String interest;
    private String tendencies;
    private String character;
    private String proFile;

    public static RecommendResponse toBuilder(MemberDto memberDto) {
        return RecommendResponse.builder()
            .memId(memberDto.getMemId())
            .memName(memberDto.getMemName())
            .gender(memberDto.getGender())
            .nickName(memberDto.getNickName())
            .birthDate(memberDto.getBirthDate())
            .age(memberDto.getAge())
            .location(memberDto.getLocation())
            .memInfo(memberDto.getMemInfo())
            .occupation(memberDto.getOccupation())
            .education(memberDto.getEducation())
            .mbti(memberDto.getMbti())
            .tall(memberDto.getTall())
            .religion(memberDto.getReligion())
            .smock(memberDto.getSmock())
            .status(memberDto.getStatus())
            .registDate(memberDto.getRegistDate())
            .cookie(memberDto.getCookie())
            .interest(memberDto.getInterest())
            .tendencies(memberDto.getTendencies())
            .character(memberDto.getCharacter())
            .proFile(memberDto.getProFile())
            .build();


    }
}
