package com.spark.member.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InterestConvert implements Converter<String, Interest> {

    // Json이 아닐 경우 Converter 인터페이스를 구현하여 문자열을 Interest enum으로 변환
    @Override
    public Interest convert(String str) {
        return Interest.fromDescription(str); // Interest enum의 fromDescription 메서드를 호출하여 변환
    }
}
