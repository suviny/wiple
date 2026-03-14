package com.suviny.wiple.domain.user.mapper;

import com.suviny.wiple.domain.user.dto.SignUpRequest;
import com.suviny.wiple.domain.user.dto.UserRoleRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * 가입된 이메일인지 확인한다.
     */
    boolean selectExistsByEmail(String email);

    /**
     * 사용 중인 닉네임인지 확인한다.
     */
    boolean selectExistsByNickname(String nickname);

    /**
     * 회원 가입 정보를 저장한다.
     */
    void insertUser(SignUpRequest signUpDto);

    /**
     * 가입된 회원의 권한을 저장한다.
     */
    void insertUserRole(UserRoleRequest userRoleDto);

    /**
     * 로컬 가입시 인증 수단인 비밀번호를 별도로 저장한다.
     */
    void insertCredential(@Param("userId") Long userId, @Param("password") String password);
}