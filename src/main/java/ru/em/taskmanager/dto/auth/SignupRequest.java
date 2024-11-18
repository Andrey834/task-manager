package ru.em.taskmanager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.em.taskmanager.validator.annotation.WhiteSpace;

@Schema(description = "Объект для регистрации пользователя")
public record SignupRequest(
        @Schema(description = "Электронная почта для регистрации пользователя -> Например example@example.ru")
        @NotBlank(message = "Электронная почта является обязательным для заполнения")
        @WhiteSpace(message = "Электронная почта не должна содержать пробелов")
        @Length(min = 2, max = 350, message = "Максимальное количество символов для электронной почты: 350")
        @Email(message = "Электронная почта не соответсвует формату")
        String email,
        @Schema(description = "Пароль для регистрации пользователя -> Минимальное количество символом 6")
        @NotBlank(message = "Пароль является обязательным для заполнения")
        @WhiteSpace(message = "Пароль не должен содержать пробелов")
        @Length(min = 6, message = "Минимальное количество символов для пароля: 6")
        String password,
        @Schema(description = "*Необязательное поле(необходимо для регистрации пользователя с правами администратора")
        String secret) {
}
