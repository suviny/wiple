package com.suviny.wiple.global.common.response;

import lombok.Getter;

/**
 * 요청이 정상적으로 처리되었을 때 클라이언트에게 반환될 성공 응답 클래스
 *
 * @param <T>   응답 본문에 포함될 요청 처리에 대한 결과 데이터의 타입
 *
 * @see ApiResponseBodyAdvice
 */

@Getter
public class ApiResponse<T> extends AbstractResponse {

    private final T data;

    private ApiResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    /**
     * 성공 응답 객체를 생성하여 반환한다.
     *
     * @param data  요청 처리 후 그 결과에 대한 데이터
     * @return      {@link ApiResponse} 객체
     * @param <T>   결과 데이터 타입
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResponseEnum.OK.getCode(), ResponseEnum.OK.getMessage(), data);
    }
}