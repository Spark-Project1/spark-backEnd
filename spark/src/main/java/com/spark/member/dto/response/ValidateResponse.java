package com.spark.member.dto.response;

import com.spark.member.dto.MemberDto;
import lombok.Getter;

@Getter
public class ValidateResponse {

  private Boolean valid;

  private MemberDto member;

  public ValidateResponse(Boolean valid, MemberDto member) {
    this.valid = valid;
    this.member = member;
  }
  
  public static ValidateResponse available(MemberDto member) {
    return new ValidateResponse(true, member);
  }
  
}
