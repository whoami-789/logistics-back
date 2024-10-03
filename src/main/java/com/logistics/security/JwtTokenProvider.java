package com.logistics.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey; // Секретный ключ для шифрования

    @Value("${jwt.expiration}")
    private long expirationTime; // Время жизни токена

    @PostConstruct
    public void init() {
        System.out.println("Secret Key: " + secretKey);
        System.out.println("Expiration Time: " + expirationTime);
    }

    // Метод для генерации JWT
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Метод для извлечения имени пользователя из токена
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // Установите секретный ключ для парсинга
                .parseClaimsJws(token) // Парсинг токена
                .getBody(); // Получение тела токена
        return claims.getSubject(); // Возвращение имени пользователя
    }

    // Метод для проверки, действителен ли токен
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // Парсинг токена с использованием секретного ключа
            return true; // Если парсинг успешен, токен действителен
        } catch (Exception e) {
            return false; // Если произошла ошибка, токен недействителен
        }
    }

    // Метод для извлечения даты истечения токена
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration(); // Возвращение даты истечения
    }
}
