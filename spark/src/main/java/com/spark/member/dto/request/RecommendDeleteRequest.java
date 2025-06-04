package com.spark.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RecommendDeleteRequest {

    @NotBlank(message="삭제를 시도하는 사용자의 정보가 없습니다.")
    private String hiddenId;
    @NotBlank(message="추천목록에 삭제할 대상의 정보가 없습니다.")
    private String hiddenTarget;

}
