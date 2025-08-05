package com.spark.base.web.handler;

import java.util.HashMap;
import java.util.Map;

import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spark.base.exception.CustomException;
import com.spark.base.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {


//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleLoginFail(CustomException e) {
//
//        ErrorResponse error = new ErrorResponse(e.getStatusCode(), "요청 오류", e.getMessage());
//
//        return ResponseEntity.status(e.getStatusCode()).body(error);
//
//    }

    @ExceptionHandler(SparkException.class)
    public ResponseEntity<ErrorResponse> handleSparkException(SparkException ex) {
        // SparkException에서 에러 코드를 가져옴 enum값.
        SparkErrorCode code = ex.getErrorCode();

        return ResponseEntity.status(code.getHttpStatus())
            .body(new ErrorResponse(code.getCode(), code.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("SPARK_999", "알 수 없는 서버 오류가 발생했습니다."));
    }


}
