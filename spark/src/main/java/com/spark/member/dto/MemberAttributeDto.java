package com.spark.member.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberAttributeDto {

    private String memId;
    private String code;
    private String type;


}
