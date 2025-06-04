package com.spark.member.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InsertMemberInfoRequest {

    @NotBlank(message="닉네임값이 없습니다.")
    private String nickName;
    @NotBlank(message = "장소값이 없습니다.")
    private String location;
    @NotBlank(message="성별값이 없습니다.")
    private String gender;
    @NotBlank(message="생일값이 없습니다.")
    private String birthDate;
    @NotBlank(message="직업값이 없습니다.")
    private String occupation;
    @NotBlank(message="학력값이 없습니다.")
    private String education;
    @NotBlank(message="mbti값이 없습니다.")
    private String mbti;
    @NotBlank(message="키값이 없습니다.")
    private String tall;
    @NotBlank(message="종교값이 없습니다.")
    private String religion;
    @NotBlank(message="흡연값이 없습니다.")
    private String smock;
    @NotBlank(message="흥미값이 없습니다.")
    private String[] interest;
    @NotBlank(message="연애성향값이 없습니다.")
    private String[] tendencies;
    @NotBlank(message="특징값이 없습니다.")
    private String[] character;
    @NotBlank(message="멤버소개값이 없습니다.")
    private String memInfo;


}
