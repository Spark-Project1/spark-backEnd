package com.sp.boot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sp.boot.dto.ChatListDto;
import com.sp.boot.dto.MessageDto;

@Mapper
public interface ChatMapper {
	
	List<ChatListDto> chatList(String memId);
	
	List<MessageDto> message(Map<String, Object> map);

}
