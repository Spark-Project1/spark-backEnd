package com.spark.member.model;


import com.spark.member.common.Smock;
import lombok.*;

import java.sql.Date;
import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Member {

    private String memId;
    private String memPwd;
    private String memName;
    private String gender;
    private String nickName;
    private Date birthDate; // 문자열로받은 날짜 date타입으로 변경
    private String location;
    private String memInfo;
    private String occupation; // 직업
    private String education; // 학력
    private String mbti;
    private String tall;
    private String religion; // 종교
    private Smock smock; // 흡연
    private String status;
    private Date registDate;
    private int cookie;
    private String interest; // 관심사
    private String tendencies; // 연애성향
    private String character; // 특징
    private String proFile; // 사진 경로
    private int age; // 회원 나이









}
