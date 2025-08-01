package com.spark.member.dto.response;

import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DetailMemberInfoResponse {


    private String memId;
    private String memName;
    private String gender;
    private String nickName;
    private Date birthDate;
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
    private String interest;
    private String tendencies;
    private String character;
    private String proFile;
    private int age;


    public static DetailMemberInfoResponse from(Member member) {
        return DetailMemberInfoResponse.builder()
                .memId(member.getMemId())
                .memName(member.getMemName())
            .gender(member.getGender())
            .nickName(member.getNickName())
            .birthDate(member.getBirthDate())
            .location(member.getLocation())
            .memInfo(member.getMemInfo())
            .occupation(member.getOccupation())
            .education(member.getEducation())
            .mbti(member.getMbti())
            .tall(member.getTall())
            .religion(member.getReligion())
            .smock(member.getSmock())
            .status(member.getStatus())
            .registDate(member.getRegistDate())
            .interest(member.getInterest())
            .tendencies(member.getTendencies())
            .character(member.getCharacter())
            .proFile(member.getProFile())
            .age(member.getAge())
            .build();

    }


}
