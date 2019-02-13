package com.kingcjy.main.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/{facebook|google|kakao}/complete")
    public String loginComplete(HttpSession httpSession) {
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> map = (HashMap<String, String>) auth2Authentication.getUserAuthentication().getDetails();

        httpSession.setAttribute("user", User.builder().build());
        return "redirect:/board/list";
    }
}
