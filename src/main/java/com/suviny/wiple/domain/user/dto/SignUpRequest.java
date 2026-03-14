package com.suviny.wiple.domain.user.dto;

import com.suviny.wiple.domain.user.type.AuthType;
import com.suviny.wiple.global.common.constant.StatusValues;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class SignUpRequest {

    private Long id;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일의 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$",
             message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함한 8~16자로 입력해주세요.")
    @Setter
    private String password;

    @NotBlank(message = "비밀번호를 한 번 더 입력해주세요.")
    private String confirmPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{3,20}$", message = "닉네임은 영문과 숫자를 포함한 3~20자로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 2~5자의 한글로 입력해주세요.")
    private String name;

    @Setter
    private AuthType authType;

    private int isEnabled = StatusValues.YES.getNumeric();
    private int isLocked = StatusValues.NO.getNumeric();
    private String deletedYn = StatusValues.NO.getLiteral();
}