package com.mercy.tarot.dto;

public record PasswordResetRequest(String email, String newPassword, String token) {
}
