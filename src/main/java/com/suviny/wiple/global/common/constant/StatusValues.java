package com.suviny.wiple.global.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로젝트 전반에서 사용되는 상태 값 처리를 위해 서로 다른 타입의 데이터들을 상응되는 의미별로 정의 및 관리하기 위한 열거형
 */

@AllArgsConstructor
@Getter
public enum StatusValues {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int numeric;

    private final String literal;

    private final boolean bool;

    public static boolean toBoolean(int value) {
        for (StatusValues e : values()) {
            if (e.getNumeric() == value) {
                return e.isBool();
            }
        }
        throw new IllegalArgumentException("'" + value + "' is an unsupported numeric status value.");
    }
}