package com.suviny.wiple.domain.user.type;

import com.suviny.wiple.global.common.type.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role implements CodeEnum {

    USER ("ROLE_USER", "일반 회원"),
    MANAGER ("ROLE_MANAGER", "시스템 매니저"),
    ADMIN ("ROLE_ADMIN", "관리자");

    private final String code;

    private final String label;
}