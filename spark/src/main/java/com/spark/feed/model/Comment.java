package com.spark.feed.model;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.feed.dto.request.ReplyCommentRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
public class Comment {

    private int comNo;
    private int feedNo;
    private String comContent;
    private int comType; // 0 : 주 댓글 1 : 대 댓글
    private int comRef;
    private Date registDate;
    private String memId;
    private String proFile;
    private String nickName;
    private int age;


    private Comment(String memId, String comContent, int feedNo){
        if(memId == null || memId.isEmpty() || comContent == null || comContent.isEmpty()){
            throw new SparkException(SparkErrorCode.SPARK_888);
        }
        this.memId = memId;
        this.comContent = comContent;
        this.feedNo = feedNo;
    }

    public static Comment tempComment(String memId, String comContent, int feedNo){
        return new Comment(memId,comContent,feedNo);
    }


    public void setMainComment(){
        this.comType = 0;
    }

    public void setReplyComment(int comNo){
        this.comNo = comNo;
        this.comType = 1;
    }



}
