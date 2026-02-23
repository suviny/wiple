package com.suviny.wiple.global.error;

import com.suviny.wiple.global.common.response.ErrorResponse;
import com.suviny.wiple.global.common.response.ResponseEnum;
import com.suviny.wiple.global.utils.ErrorUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * 애플리케이션 전역에서 발생하는 예외를 처리하는 클래스로, 발생된 예외에 대한 실패 또는 에러 응답을 반환한다.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleValidation(BindException e) {
        return toResponseEntity(ResponseEnum.INVALID_PARAMETER, InvalidField.of(e.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        return toResponseEntity(ResponseEnum.INVALID_PARAMETER, InvalidField.of(e.getConstraintViolations()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        Class<?> requiredType = e.getRequiredType();
        String argumentType = (requiredType == null) ? "Unknown" : requiredType.getSimpleName();

        List<InvalidField> errors = InvalidField.of(
                e.getName(),
                ErrorUtils.safeToString(e.getValue()),
                String.format("%s의 타입은 %s 이어야 합니다.", e.getName(), argumentType));
        return toResponseEntity(ResponseEnum.INVALID_PARAMETER, errors);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return toResponseEntity(ResponseEnum.UNSUPPORTED_METHOD, null);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleAllUncaught(Exception e) {
        return toResponseEntity(ResponseEnum.INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<ErrorResponse> toResponseEntity(ResponseEnum responseEnum, List<InvalidField> errors) {
        boolean isInvalid = responseEnum.getStatus().is4xxClientError();
        return ResponseEntity
                .status(responseEnum.getStatus())
                .body(isInvalid ? ErrorResponse.fail(responseEnum, errors) : ErrorResponse.error(responseEnum));
    }
}