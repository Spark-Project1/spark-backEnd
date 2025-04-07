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
public class MemberDto {
	
	private String memId;
	private String memPwd;
	private String memName;
	private String gender;
	private String nickName;
	private Date birthDate;
	private String location;
	private String memInfo;
	private String occupation; // 직업
	private String education; // 학력
	private String mbti;
	private String tall;
	private String religion; // 종교
	private String smock; // 흡연
	private String status;
	private Date registDate;
	private int cookie;
	
	
	
	

}
