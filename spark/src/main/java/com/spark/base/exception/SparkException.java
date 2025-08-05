package com.spark.base.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
public class SparkException extends RuntimeException{

    private final SparkErrorCode errorCode;


    public SparkException (SparkErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }



}
