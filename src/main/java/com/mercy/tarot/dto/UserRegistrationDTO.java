package com.mercy.tarot.dto;

public class UserRegistrationDTO {
    private String password;
    private String email;
    private String name;

    // Getters and setters

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}