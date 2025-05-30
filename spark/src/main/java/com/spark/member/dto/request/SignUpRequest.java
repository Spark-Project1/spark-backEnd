package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignUpRequest {

    @NotBlank(message = "아이디값을 받지 못했습니다.")
    private String memId;
    @NotBlank(message = "비밀번호값을 받지 못했습니다.")
    private String memPwd;

}
