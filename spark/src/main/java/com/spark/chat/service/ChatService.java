package com.spark.chat.service;

import java.util.List;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;

public interface ChatService {

	List<ChatListDto> chatList(String memId);

	List<MessageDto> message(int clNo);

}
