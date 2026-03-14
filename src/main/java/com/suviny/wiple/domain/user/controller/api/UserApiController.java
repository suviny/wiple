package com.suviny.wiple.domain.user.controller.api;

import com.suviny.wiple.domain.user.dto.SignUpRequest;
import com.suviny.wiple.domain.user.service.UserService;
import com.suviny.wiple.global.common.response.ResponseEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody SignUpRequest request) {
        userService.saveUser(request);
        return ResponseEntity
                .status(ResponseEnum.OK.getStatus())
                .build();
    }

    @GetMapping("/api/v1/users/email/exists")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity
                .status(ResponseEnum.OK.getStatus())
                .body(userService.verifyDuplicateEmail(email));
    }

    @GetMapping("/api/v1/users/nickname/exists")
    public ResponseEntity<Boolean> existsByNickname(@RequestParam String nickname) {
        return ResponseEntity
                .status(ResponseEnum.OK.getStatus())
                .body(userService.verifyDuplicateNickname(nickname));
    }
}