package ru.em.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Пользователь")
public record PersonDto(
        @Schema(description = "Идентификатор пользователя")
        Long id,
        @Schema(description = "Электронная почта пользователя")
        String email,
        @Schema(description = "Доступ пользователя к системе")
        boolean enabled
) {
}
