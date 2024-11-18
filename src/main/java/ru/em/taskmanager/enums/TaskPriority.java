package ru.em.taskmanager.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Приоритет для задачи")
public enum TaskPriority {
    HIGH,
    NORMAL,
    LOW
}
