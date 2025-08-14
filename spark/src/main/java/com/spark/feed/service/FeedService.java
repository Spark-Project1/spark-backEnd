package com.spark.feed.service;

import java.util.List;
import java.util.Map;

import com.spark.feed.dto.request.CommentRequest;
import com.spark.feed.dto.request.CreateFeedRequest;
import com.spark.feed.dto.request.ReplyCommentRequest;
import com.spark.feed.dto.response.FeedDetailResponse;
import com.spark.feed.dto.response.FeedListResponse;
import org.springframework.web.multipart.MultipartFile;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;

public interface FeedService {

    List<FeedListResponse> feedList();

    FeedDetailResponse feedDetail(int feedNo);

    int createFeed(CreateFeedRequest createFeedRequest, MultipartFile uploadFile);

    int deleteFeed(int feedNo);

    int comment(CommentRequest commentRequest);

    int replyComment(ReplyCommentRequest replyCommentRequest);

}
