package com.sp.boot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sp.boot.dao.ChatDao;
import com.sp.boot.dto.ChatListDto;
import com.sp.boot.dto.MessageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final ChatDao chatDao;

	@Override
	public List<ChatListDto> chatList(String memId) {
		return chatDao.chatList(memId);
	}

	@Override
	public List<MessageDto> message(int clNo) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인중인 사용자의 아이디값
		Map<String,Object> map = new HashMap<>();
		map.put("memId", userId);
		map.put("clNo",clNo);
		return chatDao.message(map);
	}
	

}
