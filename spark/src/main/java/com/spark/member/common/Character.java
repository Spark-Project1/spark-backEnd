package com.spark.member.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import retrofit2.http.GET;

@Getter
@RequiredArgsConstructor
public enum Character {

    C1("웃음이많아요"),
    C2("예의가발라요"),
    C3("긍정적인마인드"),
    C4("솔직해요"),
    C5("다정해요"),
    C6("배려심이깊어요"),
    C7("털털해요"),
    C8("동물을좋아해요"),
    C9("섬세해요"),
    C10("수줍어요"),
    C11("활발해요"),
    C12("성실해요"),
    C13("조용해요"),
    C14("강아지상"),
    C15("고양이상"),
    C16("웃는게예뻐요"),
    C17("비율이좋아요"),
    C18("하얀피부"),
    C19("큰눈"),
    C20("타투"),
    C21("섹시해요");

    private final String character;

    @JsonCreator
    public static Character fromDescription(String str) {
        if (str == null || str.isBlank()) return null;
        for (Character c : values()) {
            if (c.getCharacter().equals(str)) return c;
        }
        throw new IllegalArgumentException("Character 정보가 올바르지 않습니다.");
    }

    @JsonValue
    public String toJson() {
        return character;
    }


}
