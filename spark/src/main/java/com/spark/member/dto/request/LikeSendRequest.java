package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LikeSendRequest {


    @NotBlank(message = "좋아요 신청자의 정보가 비었습니다.")
    private String requestId;
    @NotBlank(message = "좋아요를 수신할 대상의 정보가 비었습니다.")
    private String responseId;

}
