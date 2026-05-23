package com.danish.student_management.controller;

import com.danish.student_management.dto.LoginRequest;
import com.danish.student_management.dto.LoginResponse;
import com.danish.student_management.dto.RegisterRequest;
import com.danish.student_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST register
    // http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201)
                .body(authService.register(request));
    }

    // POST login
    // http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}