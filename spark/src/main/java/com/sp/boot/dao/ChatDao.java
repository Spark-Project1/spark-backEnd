package com.sp.boot.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sp.boot.dto.ChatListDto;
import com.sp.boot.dto.MessageDto;
import com.sp.boot.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatDao {
	
	private final ChatMapper chatMapper;

	public List<ChatListDto> chatList(String memId) {
		return chatMapper.chatList(memId);
	}

	public List<MessageDto> message(Map<String, Object> map) {
		return chatMapper.message(map);
	}
	

}
