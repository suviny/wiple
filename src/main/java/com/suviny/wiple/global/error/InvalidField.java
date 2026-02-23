package com.suviny.wiple.global.error;

import com.suviny.wiple.global.utils.ErrorUtils;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 클라이언트의 잘못된 요청 파라미터로 인해 발생된 에러에 대한 정보를 추출 및 반환하는 클래스
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InvalidField {

    private final String field;

    private final String value;

    private final String message;

    /**
     * 단일 필드에 의해 발생된 에러의 정보를 {@link InvalidField} 리스트로 반환한다.
     *
     * @param field     에러가 발생한 필드명
     * @param value     사용자 입력값
     * @param message   에러가 발생한 원인
     * @return          에러 정보가 담긴 {@link InvalidField} 리스트
     */
    public static List<InvalidField> of(String field, String value, String message) {
        List<InvalidField> errors = new ArrayList<>();
        errors.add(new InvalidField(field, value, message));
        return errors;
    }

    /**
     * 유효성 검증 실패 또는 데이터 바인딩 과정 중에 발생된 에러에 대한 정보를 {@link InvalidField} 리스트로 반환한다.
     * <p>
     * {@code @Valid}와 {@code @ModelAttribute} 사용으로 {@link org.springframework.validation.BindException} 또는
     * {@code @Valid}와 {@code @RequestBody} 사용으로 {@link org.springframework.web.bind.MethodArgumentNotValidException}
     * 발생 시 해당 에러 정보를 처리한다.
     * </p>
     *
     * @param bindingResult     스프링에서 제공하는 바인딩 및 검증 실패에 대한 정보를 담고 있는 객체
     * @return                  에러 정보가 담긴 {@link InvalidField} 리스트
     */
    public static List<InvalidField> of(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.stream().map(error -> new InvalidField(
                error.getField(),
                ErrorUtils.safeToString(error.getRejectedValue()),
                error.getDefaultMessage()
        )).collect(Collectors.toList());
    }

    /**
     * 제약 조건 위반으로 인해 발생된 에러에 대한 정보를 {@link InvalidField} 리스트로 반환한다.
     * <p>
     * {@code @Validated} 사용으로 {@link jakarta.validation.ConstraintViolationException} 발생 시 해당 에러 정보를 처리한다.
     * </p>
     *
     * @param constraintViolations      제약 조건 위반으로 발생된 에러에 대한 정보를 담고 있는 객체
     * @return                          에러 정보가 담긴 {@link InvalidField} 리스트
     */
    public static List<InvalidField> of(Set<ConstraintViolation<?>> constraintViolations) {
        List<ConstraintViolation<?>> errors = new ArrayList<>(constraintViolations);
        return errors.stream().map(error -> new InvalidField(
                ErrorUtils.violatedProperty(error.getPropertyPath().toString()),
                ErrorUtils.safeToString(error.getInvalidValue()),
                error.getMessage()
        )).collect(Collectors.toList());
    }
}