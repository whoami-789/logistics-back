package com.logistics.repository;

import com.logistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegram(String username);

    Optional<User> findByRegistrationToken(String token);

    boolean existsByPhoneNumber(String phoneNumber);  // Проверка уникальности номера телефона

}
