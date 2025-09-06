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
public class ChatListResponse {


    private int clNo;
    private String memId;
    private String nickName;
    private String proFile;
    private String clNewMsg;
    private Date lastMsgTime;


    public static ChatListResponse from(Chat chat){
        return ChatListResponse.builder()
            .clNo(chat.getClNo())
            .memId(chat.getMemId())
            .nickName(chat.getNickName())
            .proFile(chat.getProFile())
            .clNewMsg(chat.getClNewMsg())
            .lastMsgTime(chat.getLastMsgTime())
            .build();
    }





}
