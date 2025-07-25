package com.spark.member.dto.request;

import java.sql.Date;

import com.spark.member.dto.MemberDto;
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
    @NotBlank(message = "성별이 비어있습니다.")
    private String gender;
    @NotBlank(message = "흥미 정보가 비었습니다.")
    private String interest;
    @NotBlank(message = "연애성향 정보가 비었습니다.")
    private String tendencies;
    @NotBlank(message = "특징 정보가 비었습니다.")
    private String character;

    public MemberDto toBuilder() {

        String[] interestArray = interest.split(",");

        String[] tendenciesArray = tendencies.split(",");

        String[] characterArray = character.split(",");

        return MemberDto.builder()
            .memId(memId)
            .gender(gender)
            .interestArray(interestArray)
            .tendenciesArray(tendenciesArray)
            .characterArray(characterArray)
            .build();

    }

}





