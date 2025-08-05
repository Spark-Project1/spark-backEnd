package com.spark.chat.service;

import java.util.List;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import com.spark.chat.dto.request.MessageSendRequest;

public interface ChatService {

    List<ChatListDto> chatList(String memId);

    List<MessageDto> message(int clNo);

    int sendMessage(int chatNo, MessageSendRequest messageSendRequest);
}
