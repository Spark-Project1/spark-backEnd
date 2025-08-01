package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class LikeRequest {

    @NotBlank(message = "requestId는 필수 입력값입니다.")
    private String requestId;
    @NotBlank(message = "responseId는 필수 입력값입니다.")
    private String responseId;



}
