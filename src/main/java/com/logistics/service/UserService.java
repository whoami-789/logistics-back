package com.logistics.service;

import com.logistics.model.User;
import com.logistics.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Добавлено поле


    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        // Проверка на уникальность номера телефона
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Пользователь с таким номером телефона уже существует.");
        }

        // Шифруем пароль перед сохранением
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Генерация уникального токена регистрации
        user.setRegistrationToken(UUID.randomUUID().toString());

        // Сохранение пользователя
        return userRepository.save(user);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }


    public User getUserByPhoneNumber(String pnumber) {
        return userRepository.findByPhoneNumber(pnumber).orElseThrow(() ->
                new IllegalArgumentException("User with phone number " + pnumber + " not found.")
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByRegistrationToken(String token) {
        return userRepository.findByRegistrationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким токеном не найден."));
    }


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с номером телефона: " + phoneNumber));
        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), user.getPassword(), new ArrayList<>());
    }

    public void encryptExistingPasswords() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }


}
