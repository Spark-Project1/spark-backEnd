package com.sp.boot.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sp.boot.dto.FeedDto;

public interface FeedService {

	List<FeedDto> feedList();

	Map<String, Object> feedDetail(int feedNo);

	int createFeed(FeedDto feed, MultipartFile uploadFile);

	int deleteFeed(int feedNo);

}
