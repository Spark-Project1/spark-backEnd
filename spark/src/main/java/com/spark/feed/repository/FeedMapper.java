package com.spark.feed.repository;

import java.util.List;
import java.util.Map;

import com.spark.feed.model.Feed;
import org.apache.ibatis.annotations.Mapper;

import com.spark.chat.dto.CommentDto;
import com.spark.feed.dto.FeedDto;

@Mapper
public interface FeedMapper {

    List<Feed> feedList();

    FeedDto feedDetail(int feedNo);

    List<String> feedComment(int feedNo);

    int createFeed(FeedDto feed);

    String searchURL(int feedNo);

    int deleteFeed(int feedNo);

    int updateFeed(FeedDto feed);

    int comment(CommentDto comment);

    String feedUrlCheck(FeedDto feed);

    int replyComment(CommentDto comment);


}
