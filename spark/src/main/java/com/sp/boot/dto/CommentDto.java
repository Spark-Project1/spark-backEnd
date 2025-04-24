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
	private String comType; // 0. 댓글 1. 대댓글
	private String comRef;  
	private String registDate;
	private String memId;
	private String proFile;

}
