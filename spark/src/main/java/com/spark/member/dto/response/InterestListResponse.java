package com.spark.member.dto.response;

import com.spark.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class InterestListResponse {

    private String memId;
    private String nickName;
    private String proFile;
    private int age;



    public static InterestListResponse from(Member member) {

        return InterestListResponse.builder()
            .memId(member.getMemId())
            .nickName(member.getNickName())
            .proFile(member.getProFile())
            .age(member.getAge())
            .build();

    }




}
