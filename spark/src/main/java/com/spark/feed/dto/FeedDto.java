package com.spark.feed.dto;

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
public class FeedDto {
	
	private int feedNo;
	private String feedContent;
	private String feedURL;
	private Date RegistDate;
	private Date modifyDate;
	private String feedId;
	private String status;
	private int likeCount;
	
	

}
