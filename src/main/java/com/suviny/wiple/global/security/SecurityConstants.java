package com.suviny.wiple.global.security;

/**
 * 스프링 시큐리티 설정과 관련된 상수들을 정의 및 관리하기 위한 상수 클래스
 */

public final class SecurityConstants {

    private SecurityConstants() {}

    /**
     * 별도의 인증 요구 없이 접근 가능한 요청 URL들을 정의한 상수 배열
     */
    public static final String[] ALLOWED_URLS = {
            /* 웹 페이지 요청 URL */
            "/",
            "/sign-up",
            /* 인증 관련 API URL */
            "/api/v1/login",
            "/api/v1/logout",
            "/api/v1/user",
            "/api/v1/user/exists/**",
            /* 기타 URL */
            "/h2-console/**"
    };

    /**
     * 스프링 시큐리티 필터 체인에서 제외할 정적 리소스의 경로들을 정의한 상수 배열
     */
    public static final String[] STATIC_RESOURCES = {
            "/favicon.ico",
            "/css/**",
            "/js/**",
            "/images/**",
            "/fonts/**"
    };
}