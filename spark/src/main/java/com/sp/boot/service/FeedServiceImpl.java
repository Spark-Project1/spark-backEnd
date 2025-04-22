package com.sp.boot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sp.boot.dao.FeedDao;
import com.sp.boot.dto.CommentDto;
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


	@Override
	public Map<String, Object> feedDetail(int feedNo) {
		
		FeedDto feedDto = feedDao.feedDetail(feedNo);
		List<String> comDto = feedDao.feedComment(feedNo);
		
		Map<String,Object> map = new HashMap<>();
		map.put("feedDto", feedDto);
		map.put("comDto", comDto);
		
		return map;
	}
	
	
	

}
