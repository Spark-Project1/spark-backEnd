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

    @NotBlank(message = "회원 정보가 없습니다.")
    private String memId;


    public Member toEntity() {
        return Member.builder()
            .memId(this.memId)
            .build();
    }

}


