package com.spark.feed.model;


import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@Getter
public class Feed {

    private int feedNo;
    private String feedContent;
    private String feedURL;
    private String memId;
    private Date registDate;
    private Date modifyDate;
    private String status;
    private int feedLike;

    private String nickName;
    private String proFile;
    private int age;

    private Feed(String memId,String feedContent){
        if(memId == null || memId.isEmpty() || feedContent == null || feedContent.isEmpty()){
            throw new SparkException(SparkErrorCode.SPARK_888);
        }
        this.memId = memId;
        this.feedContent = feedContent;
    }


    public static Feed tempFeed(String memId,String feedContent){
        return new Feed(memId,feedContent);
    }


    public void updateFeedURL(String feedURL){
        this.feedURL = feedURL;
    }

    public void activeStatus(){
        this.status = "Y";
    }




}
