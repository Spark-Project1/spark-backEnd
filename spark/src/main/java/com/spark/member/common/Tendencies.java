package com.spark.member.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum Tendencies {

    B1("결혼의향있음"),
    B2("진지한연애"),
    B3("연애부터"),
    B4("친구부터"),
    B5("다정한스킨십"),
    B6("상대에게맞춰줘요"),
    B7("함께시간보내기"),
    B8("취미공유"),
    B9("표현을잘해요"),
    B10("꼼꼼한계획"),
    B11("집에서놀기"),
    B12("놀이공원가기"),
    B13("산책하기"),
    B14("같이운동하기"),
    B15("같이게임하기"),
    B16("콘서트관람"),
    B17("여행가기"),
    B18("잘모르겠음");

    private final String tendencies;

    @JsonCreator
    public static Tendencies fromDescription(String str) {
        if (str == null || str.isBlank()) return null;
        for (Tendencies tendency : values()) {
            // 경향 문자열과 일치하는 Tendencies enum을 반환
            if (tendency.getTendencies().equals(str)) return tendency;
        }
        throw new IllegalArgumentException("경향이 올바르지 않습니다: " + str);
    }


}
