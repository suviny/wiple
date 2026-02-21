package com.suviny.wiple.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 응답 본문 구성시 사용될 응답 코드와 관련 상태 정보들을 정의 및 관리하는 열거형
 */

@AllArgsConstructor
@Getter
public enum ResponseEnum {

    /**
     * 공통 성공 응답 코드
     */
    OK (HttpStatus.OK, 100000, "요청이 정상적으로 처리되었습니다."),

    /**
     * 공통 에러 응답 코드
     */
    INVALID_PARAMETER (HttpStatus.BAD_REQUEST, -800100, "요청 파라미터가 유효하지 않습니다."),
    RESOURCE_NOT_FOUND (HttpStatus.NOT_FOUND, -804200, "요청하신 리소스를 찾을 수 없습니다."),
    UNSUPPORTED_METHOD (HttpStatus.METHOD_NOT_ALLOWED, -805100, "지원하지 않는 HTTP 메소드 요청입니다."),
    INTERNAL_SERVER_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, -900000, "내부 서버에 에러가 발생했습니다."),

    /**
     * 사용자 및 인증 관련 응답 코드
     */
    LOGIN_REQUIRED (HttpStatus.UNAUTHORIZED, -801310, "사용자 인증이 필요합니다."),
    ACCESS_DENIED (HttpStatus.FORBIDDEN, -803310, "접근 권한이 없습니다."),
    USER_NOT_FOUND (HttpStatus.NOT_FOUND, -804210, "요청하신 사용자 정보를 찾을 수 없습니다.")
    ;


    private final HttpStatus status;

    private final int code;

    private final String message;
}