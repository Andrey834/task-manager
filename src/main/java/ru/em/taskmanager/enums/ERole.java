package ru.em.taskmanager.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "Роли Пользователей")
@Getter
@RequiredArgsConstructor
public enum ERole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;
}
