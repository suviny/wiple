package com.suviny.wiple.global.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로젝트 전반에서 사용되는 상태값의 처리를 위해 서로 다른 타입의 데이터들을 상응되는 의미별로 정의 및 관리하는 열거형
 */

@AllArgsConstructor
@Getter
public enum StatusValue {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int numeric;

    private final String literal;

    private final boolean bool;

    /**
     * 정수형 상태값을 전달받아 상응되는 논리형 상태값을 반환한다.
     *
     * @param value {@code 1} 또는 {@code 0}의 정수형 상태 값
     * @return      전달된 {@code value}가 {@code 1}일 경우 {@code true}를, {@code 0}일 경우 {@code false}를 반환
     */
    public static boolean toBoolean(int value) {
        for (StatusValue e : values()) {
            if (e.getNumeric() == value) {
                return e.isBool();
            }
        }
        throw new IllegalArgumentException("'" + value + "' is an unsupported numeric status value.");
    }
}