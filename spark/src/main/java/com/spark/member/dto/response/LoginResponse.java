package com.spark.member.dto.response;

import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.model.Member;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private String memId;
    private String memPwd;
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
    private int cookie;
    private String interest;
    private String tendencies;
    private String character;
    private String proFile;
    private Integer age;


    public static LoginResponse from(Member member) {
        return LoginResponse.builder()
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
            .cookie(member.getCookie())
            .interest(member.getInterest())
            .tendencies(member.getTendencies())
            .character(member.getCharacter())
            .proFile(member.getProFile())
            .age(member.getAge())
            .build();

    }


}
