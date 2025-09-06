package com.spark.feed.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequest {

    @NotNull(message = "memId는 필수 입력 값 입니다.")
    private String memId;
    @NotNull(message = "commentComment는 필수 입력 값 입니다.")
    private String commentContent;
    @NotNull(message = "feedNo는 필수 입력 값 입니다.")
    private int feedNo;

}
