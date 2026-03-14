package com.suviny.wiple.domain.user.type;

import com.suviny.wiple.global.common.type.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthType implements CodeEnum {

    LOCAL ("LOCAL", "로컬 인증"),
    SOCIAL ("OAUTH", "소셜 인증")
    ;

    private final String code;

    private final String label;
}