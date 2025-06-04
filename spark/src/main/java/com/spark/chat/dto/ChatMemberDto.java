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
@ToString
@Builder
public class ChatMemberDto {

	private int clNo;
	private String cmId;
	private String chatTitle;
	private String memProfile;
	private String newMsg;
	private Date lastMsgTime;
	
	
}
