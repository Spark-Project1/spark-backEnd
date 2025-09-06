package com.spark.member.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InterestLikeSendRequest {

    @NotNull(message = "ImUser는 필수 입력 값 입니다.")
    private String imUser;
    @NotNull(message = "ImTarget는 필수 입력 값 입니다.")
    private String imTarget;

}
