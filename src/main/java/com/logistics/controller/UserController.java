package com.logistics.controller;

import com.logistics.model.Role;
import com.logistics.model.User;
import com.logistics.repository.RoleRepository;
import com.logistics.repository.UserRepository;
import com.logistics.security.JwtTokenProvider;
import com.logistics.security.LoginRequest;
import com.logistics.security.LoginResponse;
import com.logistics.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, RoleRepository roleRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Проверяем учетные данные
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));

            // Получаем пользователя из Authentication
            User user = userService.getUserByPhoneNumber(loginRequest.getPhoneNumber());

            // Генерируем JWT
            String token = jwtTokenProvider.generateToken(authentication.getName());

            // Извлекаем роль пользователя
            String role = user.getRole().getName(); // Получаем имя роли пользователя
            Long userId = user.getId(); // Получаем ID пользователя

            // Возвращаем ответ с токеном, ролью и ID пользователя
            LoginResponse response = new LoginResponse(token, role, userId);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            System.out.println("Ошибка аутентификации: Неверные учетные данные");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Возвращаем 401, если логин не удался
        } catch (Exception e) {
            System.out.println("Ошибка аутентификации: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Возвращаем 401, если логин не удался
        }
    }

    // Регистрация нового пользователя
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

        // Генерация ссылки для Telegram (если требуется)
        String telegramLink = "https://t.me/logistics789_test_bot?start=" + createdUser.getRegistrationToken();

        return ResponseEntity.ok("Регистрация успешна! Свяжите аккаунт с Telegram: " + telegramLink);
    }

    // Получение информации о пользователе по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserByPhoneNumber(id);
        return ResponseEntity.ok(user);
    }

    // Удаление пользователя по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/encrypt-passwords")
    public ResponseEntity<String> encryptPasswords() {
        userService.encryptExistingPasswords();
        return ResponseEntity.ok("Пароли зашифрованы успешно.");
    }
}
