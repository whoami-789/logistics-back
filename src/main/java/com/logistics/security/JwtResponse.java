package com.logistics.security;

import com.logistics.model.User;
import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private User user;

    public JwtResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    // Геттеры и сеттеры...
}

