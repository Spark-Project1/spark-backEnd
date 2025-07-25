package com.spark.member.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.spark.base.exception.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tall {

        A("140 - 145"),
        B("145 - 150"),
        C("150 - 155"),
        D("155 - 160"),
        E("160 - 165"),
        F("165 - 170"),
        G("170 - 175"),
        H("175 - 180"),
        I("180 - 185"),
        J("185 - 190");

    private final String tall;

    @JsonCreator
    public static Tall fromDescription(String str) {
        if (str == null || str.isBlank()) return null;
        for (Tall t : values()) {
            if (t.getTall().equals(str)) return t;
        }
        throw new CustomException("키 정보가 올바르지 않습니다.", 400);
    }

    @JsonValue
    public String toJson() {
        return tall;
    }




}
