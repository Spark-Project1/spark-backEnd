package com.spark.member.model;


import com.spark.base.exception.CustomException;
import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.base.util.FileUtil;
import com.spark.member.common.Character;
import com.spark.member.common.Interest;
import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.common.Tendencies;
import com.spark.member.dto.request.UpdateMemberInfoRequest;
import com.spark.member.service.MemberPreprocessor;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Getter
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
    private Tall tall;
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



    private Member(String memId, String memPwd) {
        if (memId == null || memPwd == null) {
            throw new SparkException(SparkErrorCode.SPARK_888);
        }
        this.memId = memId;
        this.memPwd = memPwd;
    }

    public static Member tempMember(String memId, String memPwd) {
        return new Member(memId, memPwd);
    }

    public void updateMember(UpdateMemberInfoRequest memberInfoRequest) {

        // "20000101" 형식의 생일 문자열을 LocalDate로 변환하여 2000-01-01 형태로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(memberInfoRequest.getBirthDate(), formatter);

        // 현재 연도와 생년을 이용하여 나이 계산
        int currentYear = Year.now().getValue();
        int birthYear = localDate.getYear();
        int calAge = currentYear - birthYear;

        this.nickName = memberInfoRequest.getNickName();
        this.location = memberInfoRequest.getLocation();
        this.gender = memberInfoRequest.getGender();
        this.birthDate = Date.valueOf(localDate);
        this.occupation = memberInfoRequest.getOccupation();
        this.education = memberInfoRequest.getEducation();
        this.mbti = memberInfoRequest.getMbti();
        this.tall = memberInfoRequest.getTall();
        this.religion = memberInfoRequest.getReligion();
        this.smock = memberInfoRequest.getSmock();
        this.memInfo = memberInfoRequest.getMemInfo();
        this.age = calAge;

    }

    public void statusActive(){
        this.status = "Y";
    }


    // 비밀번호 검증
    public void validationPassword(String inputPassword, PasswordEncoder password) {

        if (!password.matches(inputPassword, this.memPwd)) {
            throw new SparkException(SparkErrorCode.SPARK_101);
        }else{
            this.memPwd = null;
        }

    }

    // 비밀번호 암호화
    public void encryptPassword(PasswordEncoder password) {
        if (this.memPwd != null && !this.memPwd.isEmpty()) {
            this.memPwd = password.encode(this.memPwd);
        } else {
            throw new SparkException(SparkErrorCode.SPARK_888);
        }
    }


    // 프로필 사진 경로 설정
    public void updateProFile(String proFileURL) {
        this.proFile = proFileURL;
    }


}
