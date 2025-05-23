package com.spark.feed.application;

import java.util.List;
import java.util.Map;

import com.spark.feed.dto.CommentDto;
import com.spark.feed.dto.FeedDto;
import org.springframework.web.multipart.MultipartFile;

public interface FeedService {

	List<FeedDto> feedList();

	Map<String, Object> feedDetail(int feedNo);

	int createFeed(FeedDto feed, MultipartFile uploadFile);

	int deleteFeed(int feedNo);

	int updateFeed(FeedDto feed, MultipartFile uploadFile);

	int comment(CommentDto comment);

	int replyComment(CommentDto comment);

}
