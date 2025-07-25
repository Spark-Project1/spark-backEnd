package com.spark.member.dto.response;

import com.spark.member.dto.MemberDto;
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


    public static LikeListResponse toBuilder(MemberDto memberDto) {
        return LikeListResponse.builder()
            .memId(memberDto.getMemId())
            .nickName(memberDto.getNickName())
            .age(memberDto.getAge())
            .proFile(memberDto.getProFile())
            .build();
    }


}
