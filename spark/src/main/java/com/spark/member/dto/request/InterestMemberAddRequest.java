package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InterestMemberAddRequest {


    @NotBlank(message = "관심회원을 등록하는 회원의 정보가 없습니다.")
    private String imUser;
    @NotBlank(message = "관심회원으로 등록할 회원의 정보가 없습니다.")
    private String imTarget;

}
