package com.spark.chat.dto.response;


import com.spark.chat.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageListResponse {


    private int msgNo;
    private String msgContent;
    private Date msgRegistDate;
    private String memId;
    private int clNo;
    private String chatTitle;
    private String nickName;
    private String proFile;


    public static MessageListResponse from(Chat chat){
        return MessageListResponse.builder()
            .msgNo(chat.getMessage().getMsgNo())
            .msgContent(chat.getMessage().getMessageContent())
            .msgRegistDate(chat.getMessage().getMessageRegistDate())
            .memId(chat.getMessage().getMsgId())
            .clNo(chat.getClNo())
            .chatTitle(chat.getChatTitle())
            .nickName(chat.getNickName())
            .proFile(chat.getProFile())
            .build();
    }


}
