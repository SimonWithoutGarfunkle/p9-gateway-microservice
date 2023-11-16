package com.medilabo.gateway.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/auth/header")
    public String getAuthHeader(@RequestHeader("Authorization") String authHeader) {
        return authHeader;
    }
}
