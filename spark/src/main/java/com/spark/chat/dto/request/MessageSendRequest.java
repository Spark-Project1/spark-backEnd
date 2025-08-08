package com.spark.chat.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageSendRequest {

    private String nickName;
    private String messageContent;
    private String proFile;
    private String messageId;





}
