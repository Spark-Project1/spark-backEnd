package com.spark.feed.dto.response;

import com.spark.feed.model.Feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedListResponse {

    private int feedNo;
    private String feedContent;
    private String feedURL;
    private String feedId;
    private Date registDate;
    private Date modifyDate;
    private String status;
    private int feedLike;


    public static FeedListResponse from(Feed feed){
        return FeedListResponse.builder()
            .feedNo(feed.getFeedNo())
            .feedContent(feed.getFeedContent())
            .feedURL(feed.getFeedURL())
            .feedId(feed.getMemId())
            .registDate(feed.getRegistDate())
            .modifyDate(feed.getModifyDate())
            .status(feed.getStatus())
            .feedLike(feed.getFeedLike())
            .build();
    }



}
