package com.mercy.tarot.dto;

public class AuthResponse {
    private String token;
    private String message;

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Optional: toString() method for better logging/debugging
    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + (token != null ? "[PROTECTED]" : "null") + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}