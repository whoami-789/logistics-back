package com.logistics.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String telegram;
    private String country;
    private String whatsappAccount;
    private String companyName;
    private String password;
    private String registrationToken;
    private Long chatId;

    private boolean enabled;

    public User(Long id, String firstName, String lastName, String phoneNumber, String telegram, String country, String password, String registrationToken, Long chatId, boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.telegram = telegram;
        this.country = country;
        this.password = password;
        this.registrationToken = registrationToken;
        this.chatId = chatId;
        this.enabled = enabled;
    }

    public User() {
    }

    // Метод для возврата ролей и прав пользователя
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(phoneNumber)); // Возвращаем имя роли как GrantedAuthority
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

}
