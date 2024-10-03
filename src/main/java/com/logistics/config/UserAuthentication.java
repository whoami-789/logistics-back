package com.logistics.config;

import com.logistics.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication extends AbstractAuthenticationToken {

    private final User user;

    public UserAuthentication(User user) {
        super(null);
        this.user = user;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null; // Или верните список ролей пользователя, если нужно
    }
}
