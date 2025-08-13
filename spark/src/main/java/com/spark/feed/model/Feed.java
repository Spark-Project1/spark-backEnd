package com.spark.feed.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@Getter
public class Feed {

    private int feedNo;
    private String feedContent;
    private String feedURL;
    private String feedId;
    private Date registDate;
    private Date modifyDate;
    private String status;
    private int feedLike;



}
