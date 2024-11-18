package ru.em.taskmanager.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Статус для задачи")
public enum TaskStatus {
    IN_WAITING,
    IN_PROGRESS,
    COMPLETED
}
