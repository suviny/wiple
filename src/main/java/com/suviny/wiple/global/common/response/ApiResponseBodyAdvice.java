package com.suviny.wiple.global.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * 컨트롤러의 메소드에서 {@link ResponseEntity}를 이용해 반환되는 응답을 가로채 {@link ApiResponse}로 래핑하여 클라이언트에게 반환하는 클래스
 */

@RestControllerAdvice(basePackages = "com.suviny.wiple.domain")
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 컨트롤러 메소드의 반환 타입이 {@link ResponseEntity}인지 확인하며, 반환 타입이
     * {@link ResponseEntity}일 경우, {@link #beforeBodyWrite}가 실행된다.
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    /**
     * 응답 본문을 {@link ApiResponse}로 래핑하며, 본문의 {@code data}가 {@code null}일 경우, 데이터의 예상 구조에 따라
     * {@code 빈 컬렉션([])} 또는 {@code 빈 맵({})} 으로 대체한다.
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Type type = returnType.getGenericParameterType();   // 컨트롤러 메소드의 반환 타입(ResponseEntity<T>) 정보
        /* 반환 타입이 제네릭 타입 정보를 가지고 있다면, 그 제네릭 타입 정보에서 실제 데이터 타입을 추출한다. */
        if (type instanceof ParameterizedType genericType) {
            type = genericType.getActualTypeArguments()[0];
        }
        body = ObjectUtils.isEmpty(body) ? createEmptyBody(type) : body;
        return ApiResponse.success(body);
    }

    /**
     * 전달된 타입의 정보에 따라 {@code 빈 컬렉션([])} 또는 {@code 빈 맵({})}을 생성한다.
     *
     * @param type  {@link ResponseEntity}의 제네릭 타입 정보
     * @return      타입 정보가 {@link List}이거나, 그 구현체일 경우 {@code 빈 컬렉션([])}을, 그렇지 않은 경우 {@code 빈 맵({})}을 반환
     */
    private Object createEmptyBody(Type type) {
        /* 반환 타입이 제네릭 타입 정보를 가지고 있다면, 그 제네릭 타입 정보에서 타입 인자(<T>)를 제외한 순수 클래스 또는 인터페이스 정보를 추출한다. */
        if (type instanceof ParameterizedType genericType) {
            type = genericType.getRawType();
        }
        /* 타입 정보가 클래스이면서 List이거나 List의 구현체인지 확인한다. */
        if (type instanceof Class<?> clazz && List.class.isAssignableFrom(clazz)) {
            return Collections.emptyList();
        }
        return Collections.emptyMap();
    }
}