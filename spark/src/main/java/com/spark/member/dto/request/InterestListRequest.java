package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InterestListRequest {

    @NotBlank(message = "memId의 값을 받지 못했습니다.")
    private String memId;



}
