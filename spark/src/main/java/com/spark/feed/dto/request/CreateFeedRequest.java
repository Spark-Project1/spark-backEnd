package com.spark.feed.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateFeedRequest {

    @NotNull(message = "memId는 필수 입력값 입니다.")
    private String memId;
    @NotNull(message = "feedContent는 필수 입력값 입니다.")
    private String feedContent;
}
