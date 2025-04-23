package com.sp.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sp.boot.dto.FeedDto;
import com.sp.boot.service.FeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedController {
	
	private final FeedService feedService;
	
	// 피드 목록 불러오기
    @GetMapping("/feedList")
    public List<FeedDto> feedList(){
    	return feedService.feedList();
    }
    
    // 피드 상세보기
    @GetMapping("/feedList/{feedNo}")
    public Map<String, Object> feedDetail(@PathVariable int feedNo) {
    	return feedService.feedDetail(feedNo);
    }
    
    // 피드 작성
    @PostMapping("/createFeed")
    public int createFeed(FeedDto feed, MultipartFile uploadFile) {
    	return feedService.createFeed(feed, uploadFile);
    }
    
    // 피드 삭제
    @DeleteMapping("/deleteFeed")
    public int deleteFeed(@RequestParam int feedNo) {
    	return feedService.deleteFeed(feedNo);
    }
    
    // 피드 수정
    @PatchMapping("updateFeed")
    public int updateFeed(@RequestParam int feedNo) {
    	return 0;
    }
    
    
    
    
    

}
