package com.spark.member.model;


import com.spark.base.exception.CustomException;
import com.spark.base.util.FileUtil;
import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.Year;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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
    private String[] interestArray;
    private String[] tendenciesArray;
    private String[] characterArray;



    // 비밀번호 검증
    public void validationPassword(String inputPassword, PasswordEncoder password) {

        if (!password.matches(inputPassword, this.memPwd)) {
            throw new CustomException("올비르지 않은 비밀번호 입니다.", 401);
        }else{
            this.memPwd = null;
        }

    }

    // 비밀번호 암호화
    public void encryptPassword(PasswordEncoder password) {
        if (this.memPwd != null && !this.memPwd.isEmpty()) {
            this.memPwd = password.encode(this.memPwd);
        } else {
            throw new CustomException("비밀번호가 입력되지 않았습니다.", 400);
        }
    }


    // 프로필 사진 경로 설정
    public void updateProFile(String proFileURL) {
        this.proFile = proFileURL;
    }


}
