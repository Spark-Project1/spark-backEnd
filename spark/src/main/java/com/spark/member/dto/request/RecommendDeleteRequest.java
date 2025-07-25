package com.spark.member.dto.request;

import com.spark.member.dto.HiddenProfileDto;
import com.spark.member.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RecommendDeleteRequest {

    @NotBlank(message="삭제를 시도하는 사용자의 정보가 없습니다.")
    private String hiddenId;
    @NotBlank(message="추천목록에 삭제할 대상의 정보가 없습니다.")
    private String hiddenTarget;



    public HiddenProfileDto toBuilder() {
        return HiddenProfileDto.builder()
                .hiddenId(hiddenId)
                .hiddenTarget(hiddenTarget)
                .build();
    }


}
