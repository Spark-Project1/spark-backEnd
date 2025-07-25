package com.spark.member.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HiddenProfileDto {

    private String hiddenNo; // 숨김 프로필 번호
    private String hiddenId; // 숨김 프로필 ID
    private String hiddenTarget; // 숨김 대상 ID

}
