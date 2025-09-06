package com.spark.chat.controller;

import java.util.List;

import com.spark.chat.dto.request.ChatListRequest;
import com.spark.chat.dto.request.MessageListRequest;
import com.spark.chat.dto.response.ChatListResponse;
import com.spark.chat.dto.response.MessageListResponse;
import com.spark.chat.model.Chat;
import com.spark.member.config.InjectMember;
import com.spark.member.model.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;
import com.spark.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Tag(name = "Chat API", description = "채팅 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;


    @PostMapping("/chatList")
    public List<ChatListResponse> chatList(@RequestBody @Valid ChatListRequest chatListRequest  ) {
        return chatService.chatList(chatListRequest);
    }

    @GetMapping("/messageList")
    public List<MessageListResponse> message(@Valid @ModelAttribute MessageListRequest messageListRequest, @InjectMember Member member) {
        return chatService.message(messageListRequest, member);
    }


}
