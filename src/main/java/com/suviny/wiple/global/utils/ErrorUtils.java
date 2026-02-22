package com.suviny.wiple.global.utils;

/**
 * 예외 처리 과정에서 사용될 보조적인 기능들을 제공하는 유틸리티 클래스
 */

public final class ErrorUtils {

    private ErrorUtils() {}

    /**
     * 전달된 객체를 문자열로 변환한다.
     *
     * @param obj   문자열로 변환하고자 하는 객체
     * @return      전달된 객체의 값이 {@code null}일 경우 빈 문자열을, 그렇지 않은 경우 문자열로 변환 후 반환
     */
    public static String safeToString(final Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 제약 조건을 위반한 속성의 경로에서 마지막 속성명을 반환한다.
     *
     * @param path  제약 조건을 위반한 속성 경로
     * @return      경로가 {@code null}일 경우 빈 문자열을, 마지막 {@code '.'}이 존재한다면 그 이후의 속성명을, 그렇지 않다면  경로 전체를 반환
     */
    public static String violatedProperty(final String path) {
        if (path == null) {
            return "";
        }
        int lastIndex = path.lastIndexOf('.');
        return lastIndex == -1 ? path : path.substring(lastIndex + 1);
    }
}