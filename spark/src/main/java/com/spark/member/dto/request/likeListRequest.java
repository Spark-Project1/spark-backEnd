package com.spark.member.dto.request;

import com.spark.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class likeListRequest {

    @NotBlank(message = "memId 의 값을 받지 못했습니다.")
    private String memId;


    public Member toEntity() {
        return Member.builder()
            .memId(this.memId)
            .build();
    }

}


