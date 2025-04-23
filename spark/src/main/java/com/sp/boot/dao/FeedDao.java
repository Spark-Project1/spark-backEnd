package com.sp.boot.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sp.boot.dto.CommentDto;
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

	public FeedDto feedDetail(int feedNo) {
		return feedMapper.feedDetail(feedNo);
	}

	public List<String> feedComment(int feedNo) {
		return feedMapper.feedComment(feedNo);
	}

	public int createFeed(FeedDto feed) {
		return feedMapper.createFeed(feed);
	}

	public String searchURL(int feedNo) {
		return feedMapper.searchURL(feedNo);
	}

	public int deleteFeed(int feedNo) {
		return feedMapper.deleteFeed(feedNo);
	}
	
	
	
	

}
