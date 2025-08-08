package com.spark.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.chat.dto.request.MessageListRequest;
import com.spark.chat.dto.request.MessageSendRequest;
import com.spark.chat.model.Chat;
import com.spark.chat.model.Message;
import com.spark.member.model.Member;
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
    public List<Chat> message(MessageListRequest messageListRequest, Member member) {
        Chat chat = Chat.tempChat(messageListRequest.getClNo());
        chat.addMemId(member.getMemId());
        System.out.println(chat);
        return chatDao.message(chat);
    }


    @Override
    public int sendMessage(int chatNo, MessageSendRequest messageSendRequest) {
        Chat chat = Chat.tempChat(chatNo);
        chat.addMessage(messageSendRequest);
        System.out.println("서비스 : "+chat);
        int result = chatDao.sendMessage(chat);
        // 최근 메시지 업데이트
        int newMsgUpdate = chatDao.newMsgUpdate(chat);
        if(newMsgUpdate <= 0) {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
        if (result > 0) {
            return result;
        } else {
            throw new SparkException(SparkErrorCode.SPARK_999);
        }
    }


}
