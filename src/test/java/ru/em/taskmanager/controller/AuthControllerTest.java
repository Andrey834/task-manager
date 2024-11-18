package ru.em.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.em.taskmanager.dto.*;
import ru.em.taskmanager.dto.auth.RefreshRequest;
import ru.em.taskmanager.dto.auth.SigninRequest;
import ru.em.taskmanager.dto.auth.SignupRequest;
import ru.em.taskmanager.dto.auth.Tokens;
import ru.em.taskmanager.service.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;
    private final String email = "test@email.com";
    private final String password = "test";
    private final Long personId = 1L;

    @Test
    void signup() {
        String secret = "test";
        SignupRequest signupRequest = new SignupRequest(email, password, secret);
        PersonDto expectedPersonDto = new PersonDto(personId, email, true);

        when(authService.signup(signupRequest)).thenReturn(expectedPersonDto);

        ResponseEntity<PersonDto> response = authController.signup(signupRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedPersonDto, response.getBody());
    }

    @Test
    void signin() {
        SigninRequest signinRequest = new SigninRequest(email, password);
        Tokens expectedTokens = new Tokens("accessToken", "refreshToken");

        when(authService.signin(signinRequest)).thenReturn(expectedTokens);

        ResponseEntity<Tokens> response = authController.signin(signinRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTokens, response.getBody());
    }

    @Test
    void refresh() {
        RefreshRequest refreshRequest = new RefreshRequest("refreshToken");
        Tokens expectedTokens = new Tokens("accessToken", "refreshToken");

        when(authService.refresh(refreshRequest)).thenReturn(expectedTokens);

        ResponseEntity<Tokens> response = authController.refresh(refreshRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTokens, response.getBody());
    }

    @Test
    void logout() {
        ResponseEntity<Void> response = authController.logout(() -> email);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}