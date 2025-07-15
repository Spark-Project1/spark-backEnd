package com.spark.member.dto.response;

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

}
