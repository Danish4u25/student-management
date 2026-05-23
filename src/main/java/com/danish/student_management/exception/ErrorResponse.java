package com.danish.student_management.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int statusCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    // Constructor
    public ErrorResponse(int statusCode, String message,
                         String path, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    // Getters
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public LocalDateTime getTimestamp() { return timestamp; }
}