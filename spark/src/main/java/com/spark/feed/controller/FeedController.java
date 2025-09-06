package com.spark.feed.controller;

import java.util.List;
import java.util.Map;

import com.spark.feed.dto.request.CommentRequest;
import com.spark.feed.dto.request.CreateFeedRequest;
import com.spark.feed.dto.request.ReplyCommentRequest;
import com.spark.feed.dto.response.FeedDetailResponse;
import com.spark.feed.dto.response.FeedListResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;
import com.spark.feed.service.FeedService;

import lombok.RequiredArgsConstructor;

@Tag(name = "Feed API",description = "피드 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FeedController {

    private final FeedService feedService;

    // 피드 목록 불러오기
    @GetMapping("/feedList")
    public List<FeedListResponse> feedList() {
        return feedService.feedList();
    }

    // 피드 상세보기
    @GetMapping("/feedList/{feedNo}")
    public FeedDetailResponse feedDetail(@PathVariable int feedNo) {
        return feedService.feedDetail(feedNo);
    }

    // 피드 작성
    @PostMapping("/createFeed")
    public int createFeed(@Valid CreateFeedRequest createFeedRequest, MultipartFile uploadFile) {
        return feedService.createFeed(createFeedRequest, uploadFile);
    }

    // 피드 삭제
    @DeleteMapping("/deleteFeed")
    public int deleteFeed(@RequestParam int feedNo) {
        return feedService.deleteFeed(feedNo);
    }


    // 댓글 작성
    @PostMapping("/comment")
    public int comment(@RequestBody @Valid CommentRequest commentRequest) {
        return feedService.comment(commentRequest);
    }

    // 대댓글 작성
    @PostMapping("replyComment")
    public int replyComment(@RequestBody @Valid ReplyCommentRequest replyCommentRequest) {
        return feedService.replyComment(replyCommentRequest);
    }


}
