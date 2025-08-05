package com.spark.chat.model;

import lombok.Getter;

import java.sql.Date;

@Getter
public class Message {

    private int msgNo; // 메시지 번호
    private String messageContent; // 메시지 내용
    private Date messageRegistDate; // 메시지 등록 날짜
    private String messageId; // 메시지 작성자 ID
    private int chatNo; // 채팅방 번호

    public Message(int chatNo, String messageContent, String messageId) {
        this.chatNo = chatNo;
        this.messageContent = messageContent;
        this.messageId = messageId;
    }


    public void addChatNo(int ChatNo){
        this.chatNo = ChatNo;
    }



}
