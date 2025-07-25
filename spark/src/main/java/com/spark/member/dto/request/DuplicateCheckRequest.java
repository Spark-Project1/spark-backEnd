package com.spark.member.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DuplicateCheckRequest {

    @NotBlank(message = "nickName 값이 비었습니다.")
    private String nickName;



}
