package com.example.transport2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class UserApi {
    @GetMapping("admin")
    public String admin() {
        log.info("{}", SecurityContextHolder.getContext().getAuthentication());
        return "Hello admin";
    }

    @GetMapping("user")
    public String user() {
        return "Hello user";
    }

    @GetMapping("guest")
    public String guest() {
        return "Hello guest";
    }
}
