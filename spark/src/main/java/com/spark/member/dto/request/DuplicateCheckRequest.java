package com.spark.member.dto.request;


import com.spark.member.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DuplicateCheckRequest {

    @NotBlank(message = "nickName 값이 비었습니다.")
    private String nickName;


    public MemberDto toBuilder() {
        return MemberDto.builder()
                .nickName(nickName)
                .build();
    }



}
