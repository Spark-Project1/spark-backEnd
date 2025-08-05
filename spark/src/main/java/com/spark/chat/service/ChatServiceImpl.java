package com.spark.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.chat.dto.request.MessageSendRequest;
import com.spark.chat.model.Message;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import com.spark.chat.repository.ChatDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatDao chatDao;

    @Override
    public List<ChatListDto> chatList(String memId) {
        return chatDao.chatList(memId);
    }

    @Override
    public List<MessageDto> message(int clNo) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인중인 사용자의 아이디값
        Map<String, Object> map = new HashMap<>();
        map.put("memId", userId);
        map.put("clNo", clNo);
        return chatDao.message(map);
    }

    @Override
    public int sendMessage(int chatNo, MessageSendRequest messageSendRequest) {
        Message message = new Message(chatNo,messageSendRequest.getMessage(),messageSendRequest.getMemId());
        int result = chatDao.sendMessage(message);
        if(result > 0){
            return result;
        }else{
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
    }


}
