package com.suviny.wiple.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    /**
     * 로그인 페이지를 호출한다.
     */
    @GetMapping("/")
    public String login() {
        return "pages/user/login";
    }

    /**
     * 회원가입 페이지를 호출한다.
     */
    @GetMapping("/sign-up")
    public String signUp() {
        return "pages/user/signup";
    }
}