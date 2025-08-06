package com.spark.chat.repository;

import java.util.List;
import java.util.Map;

import com.spark.chat.model.ChatList;
import com.spark.chat.model.Message;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Repository;

import com.spark.chat.dto.ChatListDto;
import com.spark.chat.dto.MessageDto;

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

    public int sendMessage(Message message) {
        return chatMapper.sendMessage(message);
    }

    public ChatList createChatRoom() {
        return chatMapper.createChatRoom();
    }

    public int insertChatMember(int clNo, String memId) {
        return chatMapper.insertChatMember(clNo, memId);
    }
}
