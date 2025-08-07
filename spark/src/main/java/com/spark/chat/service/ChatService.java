package com.spark.chat.service;

import java.util.List;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import com.spark.chat.dto.request.MessageListRequest;
import com.spark.chat.dto.request.MessageSendRequest;
import com.spark.chat.model.Chat;
import com.spark.member.model.Member;

public interface ChatService {

    List<ChatListDto> chatList(String memId);

    List<Chat> message(MessageListRequest messageListRequest, Member member);

    int sendMessage(int chatNo, MessageSendRequest messageSendRequest);
}
