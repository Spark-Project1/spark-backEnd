package com.spark.chat.repository;

import java.util.List;
import java.util.Map;

import com.spark.chat.model.ChatList;
import com.spark.chat.model.Message;
import org.apache.ibatis.annotations.Mapper;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;

@Mapper
public interface ChatMapper {
	
	List<ChatListDto> chatList(String memId);
	
	List<MessageDto> message(Map<String, Object> map);

    int sendMessage(Message message);

    ChatList createChatRoom();

    int insertChatMember(int clNo, String memId);
}
