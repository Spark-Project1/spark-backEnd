package com.spark.member.dto.request;


import com.spark.member.common.Character;
import com.spark.member.common.Interest;
import com.spark.member.common.Smock;
import com.spark.member.common.Tall;
import com.spark.member.common.Tendencies;
import com.spark.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateMemberInfoRequest {

    @NotBlank(message = "닉네임값이 없습니다.")
    private String nickName;
    @NotBlank(message = "장소값이 없습니다.")
    private String location;
    @NotBlank(message = "성별값이 없습니다.")
    private String gender;
    @NotBlank(message = "생일값이 없습니다.")
    private String birthDate;
    @NotBlank(message = "직업값이 없습니다.")
    private String occupation;
    @NotBlank(message = "학력값이 없습니다.")
    private String education;
    @NotBlank(message = "mbti값이 없습니다.")
    private String mbti;
    @NotBlank(message = "키값이 없습니다.")
    private Tall tall;
    @NotBlank(message = "종교값이 없습니다.")
    private String religion;
    @NotNull(message = "흡연값이 없습니다.")
    private Smock smock;
    @NotBlank(message = "흥미값이 없습니다.")
    private List<Interest> interest;
    @NotBlank(message = "연애성향값이 없습니다.")
    private List<Tendencies> tendencies;
    @NotBlank(message = "특징값이 없습니다.")
    private List<Character> character;
    @NotBlank(message = "멤버소개값이 없습니다.")
    private String memInfo;




}
