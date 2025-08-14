package com.spark.feed.dto.request;


import lombok.Getter;

@Getter
public class ReplyCommentRequest {


    private int feedNo;
    private String comContent;
    private int comNo;
    private String memId;


}
