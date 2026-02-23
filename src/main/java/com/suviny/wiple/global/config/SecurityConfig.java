package com.suviny.wiple.global.config;

import com.suviny.wiple.global.security.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 사용자 비밀번호의 암호화를 위한 {@link PasswordEncoder} 빈 등록
     *
     * @return 해시 함수를 사용하는 {@link BCryptPasswordEncoder} 객체 반환
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 보안 필터 체인 구성을 통해 HTTP 요청에 대한 보안 정책을 설정한다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            /* HTTP 기본 인증, 폼 로그인, CSRF 보호 비활성화 */
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            /* H2 콘솔 접속을 위한 보안 헤더(X-FRAME-OPTIONS) 설정 : 동일 도메인 내에서만 접속 허용 */
            .headers(header -> header
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            /* 세션 관리 설정 */
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/"))
            /* HTTP 요청 인가 설정 구성 */
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SecurityConstants.ALLOWED_URLS).permitAll()
                .anyRequest().authenticated());
        return http.build();
    }

    /**
     * 정적 리소스를 시큐리티 보안 필터 적용 대상에서 제외한다.
     */
    @Bean
    public WebSecurityCustomizer customizer() {
        return (web) -> web.ignoring()
                .requestMatchers(SecurityConstants.STATIC_RESOURCES);
    }
}