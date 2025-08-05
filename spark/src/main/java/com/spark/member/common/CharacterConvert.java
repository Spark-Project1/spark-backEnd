package com.spark.member.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CharacterConvert implements Converter<String, Character> {

    @Override
    public Character convert(String str) {
        return Character.fromDescription(str); // Character enum의 fromDescription 메서드를 호출하여 변환
    }

}
