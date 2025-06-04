package com.spark.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import com.spark.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;


    @GetMapping("/chatList")
    public List<ChatListDto> chatList(@RequestParam String memId) {
        return chatService.chatList(memId);
    }

    @GetMapping("/chatList/{clNo}")
    public List<MessageDto> message(@PathVariable int clNo) {
        return chatService.message(clNo);
    }


}
