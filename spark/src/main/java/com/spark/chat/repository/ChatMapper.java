package com.spark.chat.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;

@Mapper
public interface ChatMapper {
	
	List<ChatListDto> chatList(String memId);
	
	List<MessageDto> message(Map<String, Object> map);

}
