package com.sp.boot.service;

import java.util.List;

import com.sp.boot.dto.ChatListDto;
import com.sp.boot.dto.MessageDto;

public interface ChatService {

	List<ChatListDto> chatList(String memId);

	List<MessageDto> message(int clNo);

}
