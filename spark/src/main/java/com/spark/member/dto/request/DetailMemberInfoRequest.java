package com.spark.member.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

public class DetailMemberInfoRequest {

    @NotBlank(message = "memId는 필수 입력값입니다.")
    private String memId; // 멤버 ID


}
