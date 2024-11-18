package ru.em.taskmanager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Объект с токеном обновления")
public record RefreshRequest(
        @Schema(description = "JWT Токен(refresh) полученный при аутентификации")
        @NotNull(message = "Отсутствует токен обновления аутентификации")
        String refresh
) {
}
