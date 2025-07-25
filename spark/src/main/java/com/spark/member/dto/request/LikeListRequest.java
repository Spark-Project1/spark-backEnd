package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LikeListRequest {

    @NotBlank(message = "memId 의 값을 받지 못했습니다.")
    private String memId;



}


