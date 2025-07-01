package com.spark.member.dto.request;

import com.spark.member.dto.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class likeListRequest {

    @NotBlank(message = "memId 의 값을 받지 못했습니다.")
    private String memId;


    public Member toEntity() {
        return Member.builder()
            .memId(this.memId)
            .build();
    }

}


