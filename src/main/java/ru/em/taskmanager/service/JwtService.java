package ru.em.taskmanager.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.em.taskmanager.dto.auth.Tokens;

import java.util.List;

public interface JwtService {

    Tokens generateTokens(UserDetails userDetails);

    String extractEmail(String token);

    boolean isTokenValid(String token);

    List<?> getRoles(String token);
}
