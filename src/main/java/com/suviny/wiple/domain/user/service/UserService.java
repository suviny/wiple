package com.suviny.wiple.domain.user.service;

import com.suviny.wiple.domain.profile.mapper.ProfileMapper;
import com.suviny.wiple.domain.user.dto.SignUpRequest;
import com.suviny.wiple.domain.user.dto.UserRoleRequest;
import com.suviny.wiple.domain.user.mapper.UserMapper;
import com.suviny.wiple.domain.user.type.AuthType;
import com.suviny.wiple.domain.user.type.Role;
import com.suviny.wiple.global.common.constant.StatusValues;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final ProfileMapper profileMapper;

    @Transactional
    public void saveUser(SignUpRequest request) {
        request.setAuthType(AuthType.LOCAL);
        userMapper.insertUser(request);

        String encoded = passwordEncoder.encode(request.getPassword());
        Long userId = request.getId();
        userMapper.insertCredential(userId, encoded);

        userMapper.insertUserRole(UserRoleRequest.builder()
                .userId(userId)
                .role(Role.USER)
                .usedYn(StatusValues.YES.getLiteral())
                .build());

        profileMapper.insertProfile(userId);
    }

    @Transactional(readOnly = true)
    public boolean verifyDuplicateEmail(String email) {
        return userMapper.selectExistsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean verifyDuplicateNickname(String nickname) {
        return userMapper.selectExistsByNickname(nickname);
    }
}