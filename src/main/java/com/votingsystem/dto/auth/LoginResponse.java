package com.votingsystem.dto.auth;

public class LoginResponse {
    private String token;
    private String username;
    private String role;
    private String message;
    private boolean success;

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
        this.success = true;
    }

    public LoginResponse(String token, String username, String role, String message) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.message = message;
        this.success = token != null;
    }

    // Error response constructor
    public LoginResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}