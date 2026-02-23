package com.suviny.wiple.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suviny.wiple.global.error.InvalidField;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 사용자의 잘못된 요청이나, 요청 처리 중에 문제가 발생했을 경우 클라이언트에게 반환될 응답 클래스
 */

@Getter
public class ErrorResponse extends AbstractResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<InvalidField> errors;

    @Builder
    private ErrorResponse(int code, String message, List<InvalidField> errors) {
        super(code, message);
        this.errors = errors;
    }

    /**
     * 실패 응답 객체를 반환한다.
     *
     * @param responseEnum  응답 코드 및 관련 상태 정보들을 정의한 열거형
     * @param errors        사용자의 잘못된 요청 파라미터로 인해 발생된 필드 에러에 대한 정보를 담은 리스트
     * @return              {@link ErrorResponse} 객체
     */
    public static ErrorResponse fail(ResponseEnum responseEnum, List<InvalidField> errors) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .errors(errors == null ? Collections.emptyList() : errors)
                .build();
    }

    /**
     * 에러 응답 객체를 반환한다.
     *
     * @param responseEnum  응답 코드 및 관련 상태 정보들을 정의한 열거형
     * @return              {@link ErrorResponse} 객체
     */
    public static ErrorResponse error(ResponseEnum responseEnum) {
        return ErrorResponse.builder()
                .code(responseEnum.getCode())
                .message(responseEnum.getMessage())
                .build();
    }
}