package com.sp.boot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.boot.dto.FeedDto;
import com.sp.boot.service.FeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedController {
	
	private FeedService feedService;
	
    @GetMapping("feedList")
    public List<FeedDto> feedList(){
    	return feedService.feedList();
    }

}
