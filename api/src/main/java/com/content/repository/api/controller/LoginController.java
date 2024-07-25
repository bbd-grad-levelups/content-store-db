package com.content.repository.api.controller;

import com.content.repository.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map login() {
        String token = jwtUtil.generateToken();
        return Collections.singletonMap("token", token);
    }
}
