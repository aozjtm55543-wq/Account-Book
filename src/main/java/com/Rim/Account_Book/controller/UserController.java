package com.Rim.Account_Book.controller;

import com.Rim.Account_Book.dto.UserLoginRequest;
import com.Rim.Account_Book.dto.UserSignupRequest;
import com.Rim.Account_Book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}