package com.suviny.wiple.domain.user.dto;

import com.suviny.wiple.domain.user.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRoleRequest {

    private Long userId;

    private Role role;

    private String usedYn;

    @Builder
    public UserRoleRequest(Long userId, Role role, String usedYn) {
        this.userId = userId;
        this.role = role;
        this.usedYn = usedYn;
    }
}