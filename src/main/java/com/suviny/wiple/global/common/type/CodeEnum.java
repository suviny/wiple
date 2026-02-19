package com.suviny.wiple.global.common.type;

/**
 * 데이터베이스의 컬럼과 매핑 대상이 되는 코드값을 정의한 열거형을 일관적으로 구현하기 위한 인터페이스
 *
 * @see CodeEnumTypeHandler
 */
public interface CodeEnum {

    /**
     * 데이터베이스 컬럼과 매핑될 문자열 타입의 코드값을 반환한다.
     */
    String getCode();

    /**
     * 화면 UI에 표시될 해당 코드의 라벨을 반환한다.
     */
    String getLabel();
}