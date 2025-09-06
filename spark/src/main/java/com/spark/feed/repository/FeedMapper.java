package com.spark.feed.repository;

import java.util.List;
import java.util.Map;

import com.spark.feed.model.Comment;
import com.spark.feed.model.Feed;
import org.apache.ibatis.annotations.Mapper;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;

@Mapper
public interface FeedMapper {

    List<Feed> feedList();

    Feed feedDetail(int feedNo);

    List<Comment> feedComment(int feedNo);

    int createFeed(Feed feed);

    String searchURL(int feedNo);

    int deleteFeed(int feedNo);

    int comment(Comment comment);

    String feedUrlCheck(FeedDto feed);

    int replyComment(Comment comment);


}
