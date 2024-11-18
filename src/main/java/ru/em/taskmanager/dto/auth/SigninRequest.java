package ru.em.taskmanager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Объект для аутентификации(Почта и пароль)")
public record SigninRequest(
        @Schema(description = "Электронная почта зарегистрированного пользователя -> Например example@example.ru")
        @NotBlank(message = "Отсутствует Электронная почта для аутентификации")
        @Email(message = "Электронная почта не соответсвует формату")
        String email,
        @Schema(description = "Пароль зарегистрированного пользователя -> Минимальное количество символом 6")
        @NotBlank(message = "Пароль является обязательным для аутентификации")
        String password) {
}
