package com.spark.chat.dto.response;


import com.spark.chat.dto.request.MessageSendRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendResponse {

    private int ChatNo;
    private String messageContent;
    private String messageId;
    private String nickName;
    private String proFile;


    public static MessageSendResponse from(int chatNo,MessageSendRequest request){
        return MessageSendResponse.builder()
                .ChatNo(chatNo)
                .messageContent(request.getMessage())
                .messageId(request.getMemId())
                .nickName(request.getNickName())
                .proFile(request.getProFile())
                .build();
    }



}
