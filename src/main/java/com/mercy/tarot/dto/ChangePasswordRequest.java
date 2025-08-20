package com.mercy.tarot.dto;

public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

    // Default constructor
    public ChangePasswordRequest() {
    }

    // Constructor with parameters
    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    // Getters and setters
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "currentPassword='[HIDDEN]'" +
                ", newPassword='[HIDDEN]'" +
                '}';
    }
}