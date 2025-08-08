package com.spark.chat.repository;

import java.util.List;
import java.util.Map;

import com.spark.chat.model.Chat;
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

	public List<Chat> message(Chat chat) {
		return chatMapper.message(chat);
	}

    public int sendMessage(Chat message) {
        return chatMapper.sendMessage(message);
    }

    public Chat createChatRoom() {
        return chatMapper.createChatRoom();
    }

    public int insertChatMember(int clNo, String memId) {
        return chatMapper.insertChatMember(clNo, memId);
    }

    public int newMsgUpdate(Chat chat) {
        return chatMapper.newMsgUpdate(chat);
    }
}
