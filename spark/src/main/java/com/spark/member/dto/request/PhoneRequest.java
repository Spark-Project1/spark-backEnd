package com.spark.member.dto.request;

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
@Setter
public class PhoneRequest {

	@NotBlank(message = "번호가 입력되지 않았습니다.")
	private String phone;

	
}
