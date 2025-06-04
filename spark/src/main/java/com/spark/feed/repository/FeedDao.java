package com.spark.feed.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedDao {

    private final FeedMapper feedMapper;

    public List<FeedDto> feedList() {
        return feedMapper.feedList();
    }

    public FeedDto feedDetail(int feedNo) {
        return feedMapper.feedDetail(feedNo);
    }

    public List<String> feedComment(int feedNo) {
        return feedMapper.feedComment(feedNo);
    }

    public int createFeed(FeedDto feed) {
        return feedMapper.createFeed(feed);
    }

    public String searchURL(int feedNo) {
        return feedMapper.searchURL(feedNo);
    }

    public int deleteFeed(int feedNo) {
        return feedMapper.deleteFeed(feedNo);
    }

    public int updateFeed(FeedDto feed) {
        return feedMapper.updateFeed(feed);
    }

    public String feedUrlCheck(FeedDto feed) {
        return feedMapper.feedUrlCheck(feed);
    }

    public int comment(CommentDto comment) {
        return feedMapper.comment(comment);
    }

    public int replyComment(CommentDto comment) {
        return feedMapper.replyComment(comment);
    }


}
