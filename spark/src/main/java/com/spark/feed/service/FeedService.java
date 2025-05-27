package com.spark.feed.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;

public interface FeedService {

	List<FeedDto> feedList();

	Map<String, Object> feedDetail(int feedNo);

	int createFeed(FeedDto feed, MultipartFile uploadFile);

	int deleteFeed(int feedNo);

	int updateFeed(FeedDto feed, MultipartFile uploadFile);

	int comment(CommentDto comment);

	int replyComment(CommentDto comment);

}
