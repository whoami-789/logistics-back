package com.logistics.service;

import com.logistics.model.User;
import com.logistics.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Проверка на уникальность номера телефона
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Пользователь с таким номером телефона уже существует.");
        }

        // Генерация уникального токена регистрации
        user.setRegistrationToken(UUID.randomUUID().toString());

        // Сохранение пользователя
        return userRepository.save(user);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }


//    public User updateUser(Long id, User updatedUser) {
//        Optional<User> existingUser = userRepository.findById(id);
//        if (existingUser.isPresent()) {
//            User user = existingUser.get();
//            user.setUsername(updatedUser.getUsername());
//            user.setEmail(updatedUser.getEmail());
//            // обновляем другие поля
//            return userRepository.save(user);
//        } else {
//            throw new IllegalArgumentException("User with ID " + id + " not found.");
//        }
//    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + id + " not found.")
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByTelegram(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Преобразуем пользователя в объект UserDetails для работы с Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getTelegram())
                .password(user.getPassword())
                .authorities(user.getRole().getName()) // назначение ролей пользователю
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public User findByRegistrationToken(String token) {
        return userRepository.findByRegistrationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким токеном не найден."));
    }

}
