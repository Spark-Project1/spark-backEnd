package com.spark.member.dto.request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RecommendRequest {

    @NotBlank(message = "아이디 값이 비었습니다")
    private String memId;
    @NotBlank(message = "이름이 비어있습니다")
    private String memName;
    @NotBlank(message = "성별이 비어있습니다.")
    private String gender;
    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickName;
    @NotBlank(message = "생년월일이 비어있습니다.")
    private Date birthDate2;
    @NotBlank(message = "장소가 비어있습니다.")
    private String location;
    @NotBlank(message = "멤버 정보가 비어있습니다.")
    private String memInfo;
    @NotBlank(message = "직업이 비어있습니다.")
    private String occupation;
    @NotBlank(message = "학력이 비어있습니다.")
    private String education;
    @NotBlank(message = "mbti가 비어있습니다.")
    private String mbti;
    @NotBlank(message = "키가 비어있습니다.")
    private String tall;
    @NotBlank(message = "종교가 비어있습니다.")
    private String religion;
    @NotBlank(message = "흡연이 비어있습니다.")
    private String smock;
    @NotBlank(message = "상태가 비어있습니다.")
    private String status;
    @NotBlank(message = "생성 날짜가 비었습니다.")
    private Date registDate;
    @NotBlank(message = "쿠키 정보가 비었습니다.")
    private int cookie;
    @NotBlank(message = "흥미 정보가 비었습니다.")
    private String interest;
    @NotBlank(message = "연애성향 정보가 비었습니다.")
    private String tendencies;
    @NotBlank(message = "특징 정보가 비었습니다.")
    private String character;
    @NotBlank(message = "프로필 정보가 비어있습니다.")
    private String proFile;


}
