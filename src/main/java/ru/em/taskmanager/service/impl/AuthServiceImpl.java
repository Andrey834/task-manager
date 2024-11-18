package ru.em.taskmanager.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.em.taskmanager.dto.*;
import ru.em.taskmanager.dto.auth.RefreshRequest;
import ru.em.taskmanager.dto.auth.SigninRequest;
import ru.em.taskmanager.dto.auth.SignupRequest;
import ru.em.taskmanager.dto.auth.Tokens;
import ru.em.taskmanager.mapper.PersonMapper;
import ru.em.taskmanager.model.Person;
import ru.em.taskmanager.service.AuthService;
import ru.em.taskmanager.service.JwtService;
import ru.em.taskmanager.service.PersonService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    @Value(value = "${app.admin.secret}")
    private String adminSecret;

    @Override
    public PersonDto signup(SignupRequest request) {
        boolean admin = request.secret() != null && request.secret().equals(adminSecret);

        Person person = PersonMapper.toPerson(request);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personService.save(person, admin);
        return PersonMapper.toPersonDto(person);
    }

    @Override
    public Tokens signin(SigninRequest signinRequest) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signinRequest.email(), signinRequest.password()));

        final Person person = personService.get(signinRequest.email());

        Tokens tokens = getTokens(person);
        return tokens;
    }

    @Override
    public Tokens refresh(RefreshRequest refreshRequest) {
        final String refreshed = refreshRequest.refresh();
        final String email = jwtService.extractEmail(refreshed);
        final String savedToken = redisTemplate.opsForValue().get(email);

        if (jwtService.isTokenValid(refreshed) && refreshed.equals(savedToken)) {
            final Person person = personService.get(email);

            return getTokens(person);
        }
        throw new JwtException("Invalid JWT token");
    }

    @Override
    public void logout(String email) {
        redisTemplate.delete(email);
    }

    private Tokens getTokens(Person person) {
        final Tokens tokens = jwtService.generateTokens(person);
        Thread.ofVirtual().start(() -> redisTemplate.opsForValue().set(person.getEmail(), tokens.refresh()));
        return tokens;
    }
}
