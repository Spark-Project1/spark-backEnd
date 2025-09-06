package com.spark.chat.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatListRequest {

    @NotNull(message = "memId는 필수 입력값 입니다.")
    private String memId;

}
