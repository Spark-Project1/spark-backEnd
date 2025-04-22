package com.sp.boot.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sp.boot.dto.FeedDto;
import com.sp.boot.mapper.FeedMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedDao {
	
	private final FeedMapper feedMapper;

	public List<FeedDto> feedList() {
		return feedMapper.feedList();
	}
	
	
	
	

}
