package com.sp.boot.dto;

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
public class CommentDto {
	
	private int comNo;
	private int feedNo;
	private String comContent;
	private String comType; // 1. 댓글 2. 대댓글
	private String comRef;  
	private String registDate;

}
