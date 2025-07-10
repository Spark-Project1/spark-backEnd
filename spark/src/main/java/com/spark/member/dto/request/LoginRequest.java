package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class LoginRequest {

    @NotBlank(message = "아이디값이 비어있습니다.")
    private String memId;
    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String memPwd;

}
