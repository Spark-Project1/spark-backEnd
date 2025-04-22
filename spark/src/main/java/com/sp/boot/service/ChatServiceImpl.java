package com.sp.boot.service;

import org.springframework.stereotype.Service;

import com.sp.boot.dao.ChatDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final ChatDao chatDao;
	

}
