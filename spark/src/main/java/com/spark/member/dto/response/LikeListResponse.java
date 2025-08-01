package com.spark.member.dto.response;

import com.spark.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LikeListResponse {

    private String memId;
    private String nickName;
    private int age;
    private String proFile;


    public static LikeListResponse from(Member member) {
        return LikeListResponse.builder()
            .memId(member.getMemId())
            .nickName(member.getNickName())
            .age(member.getAge())
            .proFile(member.getProFile())
            .build();
    }


}
