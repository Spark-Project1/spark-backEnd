package com.sp.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sp.boot.dto.CommentDto;
import com.sp.boot.dto.FeedDto;

@Mapper
public interface FeedMapper {
	
	List<FeedDto> feedList();

	FeedDto feedDetail(int feedNo);
	
	List<String> feedComment(int feedNo);
}
