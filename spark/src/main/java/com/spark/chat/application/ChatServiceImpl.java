package com.spark.chat.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spark.chat.repository.ChatRepository;
import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final ChatRepository chatRepository;

	@Override
	public List<ChatListDto> chatList(String memId) {
		return chatRepository.chatList(memId);
	}

	@Override
	public List<MessageDto> message(int clNo) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인중인 사용자의 아이디값
		Map<String,Object> map = new HashMap<>();
		map.put("memId", userId);
		map.put("clNo",clNo);
		return chatRepository.message(map);
	}
	

}
