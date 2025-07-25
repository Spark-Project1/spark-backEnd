package com.spark.member.dto.response;

import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.dto.MemberDto;
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


    public static DetailMemberInfoResponse toBuilder(MemberDto memberDto) {
        return DetailMemberInfoResponse.builder()
                .memId(memberDto.getMemId())
                .memName(memberDto.getMemName())
            .gender(memberDto.getGender())
            .nickName(memberDto.getNickName())
            .birthDate(memberDto.getBirthDate())
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
            .interest(memberDto.getInterest())
            .tendencies(memberDto.getTendencies())
            .character(memberDto.getCharacter())
            .proFile(memberDto.getProFile())
            .age(memberDto.getAge())
            .build();

    }


}
