package com.danish.student_management.dto;

import com.danish.student_management.model.User;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private User.Role role;
    private Long studentId; // only needed if role is ROLE_STUDENT
}