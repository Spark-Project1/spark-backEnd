package com.spark.chat.controller;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.chat.dto.request.MessageSendRequest;
import com.spark.chat.dto.response.MessageSendResponse;
import com.spark.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/{chatNo}")
    public void sendMessage(@DestinationVariable int chatNo, MessageSendRequest messageSendRequest){
        // 메시지 DB에 등록
        int result = chatService.sendMessage(chatNo,messageSendRequest);
        if (result > 0) {
            // 클라이언트로부터 받은 메시지를 해당 채팅방으로 전송
            simpMessagingTemplate.convertAndSend("/sub/chat/"+chatNo, MessageSendResponse.from(chatNo,messageSendRequest));
        }else{
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
    }
}
