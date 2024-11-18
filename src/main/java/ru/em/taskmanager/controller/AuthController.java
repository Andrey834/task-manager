package ru.em.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.em.taskmanager.dto.*;
import ru.em.taskmanager.dto.auth.RefreshRequest;
import ru.em.taskmanager.dto.auth.SigninRequest;
import ru.em.taskmanager.dto.auth.SignupRequest;
import ru.em.taskmanager.dto.auth.Tokens;
import ru.em.taskmanager.service.AuthService;

import java.security.Principal;

@Tag(name = "Аутентификация")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя", description = "Поле secret объекта SignupRequest используется для " +
                                                                   "назначения роли администратора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация нового пользователя",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации полей объекта",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "409", description = "Ошибка: Дубликат email в системе",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))})})
    @PostMapping("/signup")
    public ResponseEntity<PersonDto> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupRequest));
    }

    @Operation(summary = "Аутентификация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аутентификация пользователя",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tokens.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("/signin")
    public ResponseEntity<Tokens> signin(@RequestBody @Valid SigninRequest signinRequest) {
        return ResponseEntity.ok(authService.signin(signinRequest));
    }

    @Operation(summary = "Обновление JWT токенов(access & refresh)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обновление JWT токенов",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tokens.class))}),
            @ApiResponse(responseCode = "400", description = "Не валидный токен обновления", content = @Content)})
    @PostMapping("/refresh")
    public ResponseEntity<Tokens> refresh(@RequestBody @Valid RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }

    @Operation(summary = "Выход из системы")
    @ApiResponse(responseCode = "204", description = "Обновление JWT токенов", content = @Content)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal) {
        authService.logout(principal.getName());
        return ResponseEntity.noContent().build();
    }
}
