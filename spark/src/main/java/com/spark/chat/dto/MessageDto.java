package com.spark.chat.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MessageDto {

	private int msgNo;
	private String msgContent;
	private Date msgRegistDate;
	private String msgId;
	private int clNo;
	private String chatTitle;
	private String nickName;
	private String proFile;
	
	
}
