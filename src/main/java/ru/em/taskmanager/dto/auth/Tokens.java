package ru.em.taskmanager.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "JWT токены(access & refresh)")
@Builder
public record Tokens(
        @Schema(description = "JWT Токен для доступа к API")
        String access,
        @Schema(description = "JWT Токен для обновления токена доступа")
        String refresh
) {
}
