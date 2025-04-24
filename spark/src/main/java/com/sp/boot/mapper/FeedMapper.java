package com.sp.boot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sp.boot.dto.CommentDto;
import com.sp.boot.dto.FeedDto;

@Mapper
public interface FeedMapper {
	
	List<FeedDto> feedList();

	FeedDto feedDetail(int feedNo);
	
	List<String> feedComment(int feedNo);
	
	int createFeed(FeedDto feed);
	
	String searchURL(int feedNo);
	
	int deleteFeed(int feedNo);
	
	int updateFeed(FeedDto feed);
	
	int comment(CommentDto comment);
	
	String feedUrlCheck(FeedDto feed);
	
	int replyComment(CommentDto comment);
	
	
}
