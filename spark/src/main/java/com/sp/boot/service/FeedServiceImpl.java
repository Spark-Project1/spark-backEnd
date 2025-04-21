package com.sp.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sp.boot.dao.FeedDao;
import com.sp.boot.dto.FeedDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{
	
	private final FeedDao feedDao;
	
	
	@Override
	public List<FeedDto> feedList() {
		return feedDao.feedList();
	}
	
	
	

}
