package com.spark.feed.dto.response;

import com.spark.feed.model.Comment;
import com.spark.feed.model.Feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeedDetailResponse {

    private int feedNo;
    private String feedContent;
    private String feedURL;
    private String memId;
    private Date registDate;
    private String status;
    private int feedLike;

    private List<Comment> comment;



    public static FeedDetailResponse from(List<Comment> comment, Feed feed){
        return FeedDetailResponse.builder()
            .feedNo(feed.getFeedNo())
            .feedContent(feed.getFeedContent())
            .feedURL(feed.getFeedURL())
            .memId(feed.getMemId())
            .registDate(feed.getRegistDate())
            .status(feed.getStatus())
            .feedLike(feed.getFeedLike())
            .comment(comment)
            .build();
    }



}
