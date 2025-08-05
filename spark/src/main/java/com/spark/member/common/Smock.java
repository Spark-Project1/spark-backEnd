package com.spark.member.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.spark.base.exception.CustomException;
import com.spark.base.exception.SparkErrorCode;
import com.spark.base.exception.SparkException;
import com.spark.base.web.handler.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Smock {

    Y("자주"), N("안함"), A("가끔");

    private final String smock;


    // Smock value를 순환하여 str값과 일치하는 값을 반환 일치하는 값이 없으면 예외 발생
    @JsonCreator
    public static Smock fromDescription(String str) {
        if (str == null || str.isBlank()) return null;
        for (Smock s : values()) {
            if (s.getSmock().equals(str)) return s;
        }
        throw new SparkException(SparkErrorCode.SPARK_888);
    }

    @JsonValue
    public String toJson() {
        return smock;
    }

}
