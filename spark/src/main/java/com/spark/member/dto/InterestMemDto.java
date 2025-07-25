package com.spark.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class InterestMemDto {

    private String imUser; // 관심회원 등록하는 회원의 ID
    private String imTarget; // 관심회원으로 등록할 회원의 ID


}

