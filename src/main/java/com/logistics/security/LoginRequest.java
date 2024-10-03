package com.logistics.security;

public class LoginRequest {
    private String phoneNumber; // Изменено на phoneNumber
    private String password;

    // Геттеры и сеттеры

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}