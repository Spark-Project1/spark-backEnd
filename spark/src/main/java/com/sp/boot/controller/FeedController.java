package com.sp.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.boot.dto.FeedDto;
import com.sp.boot.service.FeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedController {
	
	private final FeedService feedService;
	
    @GetMapping("/feedList")
    public List<FeedDto> feedList(){
    	return feedService.feedList();
    }
    
    @GetMapping("/feedList/{feedNo}")
    public Map<String, Object> feedDetail(@PathVariable int feedNo) {
    	return feedService.feedDetail(feedNo);
    }
    

}
