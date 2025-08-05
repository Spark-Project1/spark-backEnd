package com.spark.member.dto.request;

import com.spark.member.common.Character;
import com.spark.member.common.Interest;
import com.spark.member.common.Tendencies;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
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


    private List<String> interestArray;
    private List<String> tendenciesArray;
    private List<String> characterArray;

    public void toArray() {

        this.interestArray = (interest != null && !interest.isBlank())
            ? Arrays.stream(interest.split(","))
            // Interest enum에서 description을 통해 enum 객체로 변환
            .map(Interest::fromDescription)
            // enum 객체에서 name() 메서드를 호출하여 이름을 가져옴
            .map(Interest::name)
            .toList() : Collections.emptyList();

        this.tendenciesArray = (tendencies != null && !tendencies.isBlank())
            ? Arrays.stream(tendencies.split(","))
            .map(Tendencies::fromDescription)
            .map(Tendencies::name)
            .toList() : Collections.emptyList();

        this.characterArray = (character != null && !character.isBlank())
            ? Arrays.stream(character.split(","))
            .map(Character::fromDescription)
            .map(Character::name)
            .toList() : Collections.emptyList();


    }

}





