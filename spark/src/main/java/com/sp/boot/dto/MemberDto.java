package com.sp.boot.dto;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

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
	private String birthDate; // 19990101 문자열로 받기
	private Date birthDate2; // 문자열로받은 날짜 date타입으로 변경
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
	private String interest; // 관심사
	private String tendencies; // 연애성향
	private String character; // 특징
	private String proFile; // 사진 경로
	private int age;
	
	
	
    @JsonSetter("interest") // json에서 java 객체로 바꿀때 어떤 메서드를 호출할지 정의
    public void setInterest(List<String> interests) {
        this.interest = String.join(",", interests);
    }
    
    @JsonSetter("tendencies")
    public void setTendencies(List<String> tendencies) {
        this.tendencies = String.join(",", tendencies);
    }
    
    @JsonSetter("character")
    public void setCharacter(List<String> character) {
        this.character = String.join(",", character);
    }
	
	

}
