package com.sp.boot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sp.boot.dto.ChatDto;
import com.sp.boot.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {
	
	private final ChatService chatService;
	
	
//	@GetMapping("/chatList")
//	public List<ChatDto> chatList(){
//		return chatService.chatList();
//	}
	
	

}
