package com.spark.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
public class ChatList {

    private int clNo;
    private String clNewMsg;
    private Date lastMsgTime;

}
