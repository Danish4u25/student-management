package com.danish.student_management.service;

import com.danish.student_management.dto.LoginRequest;
import com.danish.student_management.dto.LoginResponse;
import com.danish.student_management.dto.RegisterRequest;
import com.danish.student_management.exception.DuplicateResourceException;
import com.danish.student_management.exception.ResourceNotFoundException;
import com.danish.student_management.model.User;
import com.danish.student_management.repository.StudentRepository;
import com.danish.student_management.repository.UserRepository;
import com.danish.student_management.security.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Manual constructor with @Lazy on AuthenticationManager
    public AuthService(UserRepository userRepository,
                       StudentRepository studentRepository,
                       PasswordEncoder passwordEncoder,
                       @Lazy AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Register a new user
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "User", "email", request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "User", "username", request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);

        if (request.getRole() == User.Role.ROLE_STUDENT
                && request.getStudentId() != null) {
            user.setStudent(
                    studentRepository.findById(request.getStudentId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Student", "id",
                                    request.getStudentId()))
            );
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "email",
                                        request.getEmail()
                                )
                        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        request.getEmail()
                );

        String token =
                jwtUtil.generateToken(
                        userDetails,
                        user.getRole().name(),
                        user.getId()
                );

        return new LoginResponse(
                token,
                user.getRole().name(),
                user.getEmail(),
                user.getUsername(),
                user.getId()
        );
    }
}