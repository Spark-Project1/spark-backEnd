package com.spark.member.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import kotlinx.serialization.Required;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Interest {

    A1("영화"),
    A2("드라마"),
    A3("유튜브"),
    A4("음악듣기"),
    A5("맛집투어"),
    A6("카페가기"),
    A7("노래방"),
    A8("게임"),
    A9("요리"),
    A10("쇼핑"),
    A11("호캉스"),
    A12("애니"),
    A13("악기연주"),
    A14("댄스"),
    A15("여행"),
    A16("등산"),
    A17("캠핑"),
    A18("낚시"),
    A19("독서"),
    A20("글쓰기"),
    A21("봉사활동");

    private final String interest;

    // Interest enum을 문자열로 변환하는 메소드
    @JsonCreator
    public static Interest fromDescription(String str) {
        if (str == null || str.isBlank()) return null;
        for (Interest interest : values()) {
            // 관심사 문자열과 일치하는 Interest enum을 반환
            if (interest.getInterest().equals(str)) return interest;
        }
        throw new IllegalArgumentException("관심사가 올바르지 않습니다: " + str);
    }


}
