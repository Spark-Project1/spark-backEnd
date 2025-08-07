package com.spark.chat.model;

import com.spark.chat.dto.request.MessageSendRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private int msgNo; // 메시지 번호
    private String messageContent; // 메시지 내용
    private String messageRegistDate; // 메시지 등록 날짜
    private String messageId; // 메시지 작성자 ID
    private String msgId;
    private String chatTitle;




    public void setMessage(MessageSendRequest messageSendRequest){
        this.messageContent = messageSendRequest.getMessageContent();
        this.messageId = messageSendRequest.getMessageId();
    }



}
