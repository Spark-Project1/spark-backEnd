package com.spark.base.web.handler;

import com.spark.base.exception.CustomException;
import com.spark.base.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleLoginFail(CustomException e) {
    ErrorResponse error = new ErrorResponse(e.getStatusCode(), "요청 오류", e.getMessage());
    return ResponseEntity.status(e.getStatusCode()).body(error);
  }

}
