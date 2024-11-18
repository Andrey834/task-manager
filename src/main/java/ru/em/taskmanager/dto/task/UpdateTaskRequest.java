package ru.em.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.em.taskmanager.enums.TaskPriority;
import ru.em.taskmanager.enums.TaskStatus;

@Schema(description = "Объект для обновления задачи(Task)")
public record UpdateTaskRequest(
        @Schema(description = "Обновление ID исполнителя задачи")
        Long executorId,

        @Schema(description = "Обновление наименование задачи")
        @NotBlank(message = "Наименование является обязательным для заполнения")
        @Length(min = 6, max = 200, message = "Количество символов для наименование: от 5 до 200")
        String title,

        @Schema(description = "Обновение описание задачи")
        @NotBlank(message = "Описание задачи является обязательным для заполнения")
        @Length(min = 6, max = 500, message = "Количество символов для описание задачи: от 5 до 500")
        String description,

        @Schema(description = "Обновение статуса задачи")
        @NotBlank(message = "Статус задачи является обязательным для заполнения")
        TaskStatus status,

        @Schema(description = "Обновление приоритета задачи")
        @NotBlank(message = "Приоритет задачи является обязательным для заполнения")
        TaskPriority priority
) {
}
