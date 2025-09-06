package com.spark.chat.repository;

import java.util.List;
import java.util.Map;

import com.spark.chat.dto.request.ChatListRequest;
import com.spark.chat.model.Chat;
import com.spark.chat.model.Message;
import org.apache.ibatis.annotations.Mapper;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;

@Mapper
public interface ChatMapper {
	
	List<Chat> chatList(ChatListRequest chatListRequest);
	
	List<Chat> message(Chat chat);

    int sendMessage(Chat message);

    int createChatRoom(Chat chat);

    int insertChatMember(int clNo, String memId);

    int newMsgUpdate(Chat chat);
}
