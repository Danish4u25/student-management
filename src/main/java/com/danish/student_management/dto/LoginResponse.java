package com.danish.student_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String role;
    private String email;
    private String username;
    private Long userId;
}