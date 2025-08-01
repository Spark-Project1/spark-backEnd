package com.spark.member.common;

import com.spark.base.exception.CustomException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TallConvert implements Converter<String, Tall> {

    @Override
    public Tall convert(String str){
       return Tall.fromDescription(str); // Tall enum의 fromDescription 메서드를 호출하여 문자열을 Tall enum으로 변환
    }

}
