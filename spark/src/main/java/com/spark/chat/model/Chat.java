package com.spark.chat.model;

import com.spark.chat.dto.request.MessageSendRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class Chat {

    private int clNo;
    private Date clNewMsg;
    private Date lastMsgTime;

    private Message message;


    // 조회용 필드
    private String nickName;
    private String proFile;
    private String memId;
    private String chatTitle;



    private Chat(int clNo) {
        this.clNo = clNo;
    }
    public static Chat tempChat(int clNo){
        return new Chat(clNo);
    }

    public void addMessage(MessageSendRequest messageRequest) {
        Message message = new Message();
        message.setMessage(messageRequest);
        this.message = message;
    }

    public void addMemId(String memId){
        this.memId = memId;
    }







}
