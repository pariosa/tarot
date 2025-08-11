package com.mercy.tarot.dto;

public record LoginRequest(String email, String password) {

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
