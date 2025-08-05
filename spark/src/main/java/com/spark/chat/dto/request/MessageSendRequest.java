package com.spark.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MessageSendRequest {

    private String nickName;
    private String message;
    private String proFile;
    private String memId;

}
