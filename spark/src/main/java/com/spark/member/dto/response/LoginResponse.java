package com.spark.member.dto.response;

import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.dto.MemberDto;
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
    private Smock smock; // 흡연
    private String status;
    private Date registDate;
    private int cookie;
    private String interest;
    private String tendencies;
    private String character;
    private String proFile;
    private Integer age;


    public static LoginResponse toBuilder(MemberDto memberDto) {
        return LoginResponse.builder()
            .memId(memberDto.getMemId())
            .memPwd(memberDto.getMemPwd())
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
            .cookie(memberDto.getCookie())
            .interest(memberDto.getInterest())
            .tendencies(memberDto.getTendencies())
            .character(memberDto.getCharacter())
            .proFile(memberDto.getProFile())
            .age(memberDto.getAge())
            .build();

    }


}
