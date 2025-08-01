package com.spark.member.common;

import com.spark.base.exception.CustomException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SmockConvert implements Converter<String, Smock> {// <String,Smock> 타입 변환 String을 Smock enum으로 변환하는 의미

    // Json이 아닐 경우 Converter 인터페이스를 구현하여 문자열을 Smock enum으로 변환
    @Override
    public Smock convert(String str) {
        return Smock.fromDescription(str); // Smock enum의 fromDescription 메서드를 호출하여 변환
    }

}
