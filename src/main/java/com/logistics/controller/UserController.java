package com.logistics.controller;

import com.logistics.model.Role;
import com.logistics.model.User;
import com.logistics.repository.RoleRepository;
import com.logistics.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Поиск роли по role_id
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new IllegalArgumentException("Роль с таким ID не найдена"));

        // Устанавливаем роль для пользователя
        user.setRole(role);

        // Проверка на уникальность номера телефона
        if (userService.existsByPhoneNumber(user.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Пользователь с таким номером телефона уже существует.");
        }

        // Создание нового пользователя
        User createdUser = userService.createUser(user);

        // Генерация ссылки для Telegram
        String telegramLink = "https://t.me/logistics789_test_bot?start=" + createdUser.getRegistrationToken();

        // Возвращаем сообщение о успешной регистрации с ссылкой на Telegram
        return ResponseEntity.ok("Вы успешно зарегистрированы! Пожалуйста, перейдите по этой ссылке, чтобы связать аккаунт с Telegram: " + telegramLink);
    }




    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
