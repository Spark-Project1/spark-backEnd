package com.spark.member.dto.request;

import com.spark.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SignUpRequest {

    @NotBlank(message = "아이디값을 받지 못했습니다.")
    private String memId;
    @NotBlank(message = "비밀번호값을 받지 못했습니다.")
    private String memPwd;


    public Member toDomain() {
        return Member.builder()
            .memId(memId)
            .memPwd(memPwd)
            .build();
    }


}
