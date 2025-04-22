package com.sp.boot.service;

import java.util.List;
import java.util.Map;

import com.sp.boot.dto.FeedDto;

public interface FeedService {

	List<FeedDto> feedList();

	Map<String, Object> feedDetail(int feedNo);

}
