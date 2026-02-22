package com.suviny.wiple.global.error;

import com.suviny.wiple.global.utils.ErrorUtils;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InvalidField {

    private final String field;

    private final String value;

    private final String message;

    public static List<InvalidField> of(String field, String value, String message) {
        List<InvalidField> errors = new ArrayList<>();
        errors.add(new InvalidField(field, value, message));
        return errors;
    }

    public static List<InvalidField> of(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.stream().map(error -> new InvalidField(
                error.getField(),
                ErrorUtils.safeToString(error.getRejectedValue()),
                error.getDefaultMessage()
        )).collect(Collectors.toList());
    }

    public static List<InvalidField> of(Set<ConstraintViolation<?>> constraintViolations) {
        List<ConstraintViolation<?>> errors = new ArrayList<>(constraintViolations);
        return errors.stream().map(error -> new InvalidField(
                ErrorUtils.violatedProperty(error.getPropertyPath().toString()),
                ErrorUtils.safeToString(error.getInvalidValue()),
                error.getMessage()
        )).collect(Collectors.toList());
    }
}