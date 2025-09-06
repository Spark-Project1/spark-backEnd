package com.spark.feed.repository;

import java.util.List;
import java.util.Map;

import com.spark.feed.model.Comment;
import com.spark.feed.model.Feed;
import org.springframework.stereotype.Repository;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedDao {

    private final FeedMapper feedMapper;

    public List<Feed> feedList() {
        return feedMapper.feedList();
    }

    public Feed feedDetail(int feedNo) {
        return feedMapper.feedDetail(feedNo);
    }

    public List<Comment> feedComment(int feedNo) {
        return feedMapper.feedComment(feedNo);
    }

    public int createFeed(Feed feed) {
        return feedMapper.createFeed(feed);
    }

    public String searchURL(int feedNo) {
        return feedMapper.searchURL(feedNo);
    }

    public int deleteFeed(int feedNo) {
        return feedMapper.deleteFeed(feedNo);
    }

    public String feedUrlCheck(FeedDto feed) {
        return feedMapper.feedUrlCheck(feed);
    }

    public int comment(Comment comment) {
        return feedMapper.comment(comment);
    }

    public int replyComment(Comment comment) {
        return feedMapper.replyComment(comment);
    }


}
