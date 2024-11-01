package com.ayoub.project_management.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;    // Field for user's email
    private String password; // Field for user's password
}
