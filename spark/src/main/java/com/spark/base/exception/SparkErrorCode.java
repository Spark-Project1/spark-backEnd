package com.spark.base.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SparkErrorCode {

    // 공통 에러 코드
    // 400 httpStatus.BAD_REQUEST : 잘못된 요청
    // 401 httpStatus.UNAUTHORIZED : 토큰이 없음, 로그인 안됨
    // 403 httpStatus.FORBIDDEN : 접근 권한 부족
    // 404 httpStatus.NOT_FOUND : 리소스 없음	없는 사용자 ID, 게시글 ID
    // 409 httpStatus.CONFLICT : 중복 회원가입, 중복 좋아요
    // 500 httpStatus.INTERNAL_SERVER_ERROR : 서버 내부 오류

    // 10x : 회원관련 에러
    SPARK_100("SPARK_100","존재하지 않는 회원입니다", HttpStatus.BAD_REQUEST),
    SPARK_101("SPARK_101","비밀번호가 일치하지 않습니다.",HttpStatus.UNAUTHORIZED),
    SPARK_102("SPARK_102","현재 가입된 회원입니다.",HttpStatus.CONFLICT),
    SPARK_103("SPARK_103","해당 닉네임은 이미 사용중입니다.",HttpStatus.CONFLICT),
    SPARK_104("SPARK_104","토큰 정보가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),

    // 11x : 좋아요, 관심 관련 에러
    SPARK_110("SPARK_110","이미 등록된 사용자 입니다.", HttpStatus.CONFLICT),


    // 2xx : 피드 관련 에러


    // 3xx : 채팅 관련 에러


    // 888 : 기타 에러
    SPARK_888("SPARK_888","입력받은 정보가 올바르지 않습니다", HttpStatus.BAD_REQUEST),

    // 999 : DB 관련 에러
    SPARK_999("SPARK_999","서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
