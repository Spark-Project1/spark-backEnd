package com.spark.member.dto.request;

import com.spark.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoginRequest {

    @NotBlank(message = "아이디값이 비어있습니다.")
    private String memId;
    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String memPwd;

    public Member toDomain() {
        return Member.builder()
            .memId(memId)
            .memPwd(memPwd)
            .build();
    }

}
