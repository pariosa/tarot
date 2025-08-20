// PasswordResetTokenResponse.java
package com.mercy.tarot.dto;

import java.time.LocalDateTime;

public record PasswordResetTokenResponse(String token, LocalDateTime expiryDate) {
}