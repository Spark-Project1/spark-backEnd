package com.sp.boot.dao;

import org.springframework.stereotype.Repository;

import com.sp.boot.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatDao {
	
	private final ChatMapper chatMapper;
	

}
