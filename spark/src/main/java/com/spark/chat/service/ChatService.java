package com.spark.chat.service;

import java.util.List;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import com.spark.chat.dto.request.ChatListRequest;
import com.spark.chat.dto.request.MessageListRequest;
import com.spark.chat.dto.request.MessageSendRequest;
import com.spark.chat.dto.response.ChatListResponse;
import com.spark.chat.dto.response.MessageListResponse;
import com.spark.chat.model.Chat;
import com.spark.member.model.Member;

public interface ChatService {

    List<ChatListResponse> chatList(ChatListRequest chatListRequest);

    List<MessageListResponse> message(MessageListRequest messageListRequest, Member member);

    int sendMessage(int chatNo, MessageSendRequest messageSendRequest);
}
