package ru.em.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.em.taskmanager.enums.TaskPriority;

@Schema(description = "Объект для добавления новой задачи")
public record TaskRequest(
        @Schema(description = "ID исполнителя задачи")
        Long executorId,

        @Schema(description = "Наименование новой задачи")
        @NotBlank(message = "Наименование является обязательным для заполнения")
        @Length(min = 6, max = 200, message = "Количество символов для наименование: от 5 до 200")
        String title,

        @Schema(description = "Описание новой задачи")
        @NotBlank(message = "Описание задачи является обязательным для заполнения")
        @Length(min = 6, max = 500, message = "Количество символов для описание задачи: от 5 до 500")
        String description,

        @Schema(description = "Приоритет новой задачи")
        @NotBlank(message = "Приоритет задачи является обязательным для заполнения")
        TaskPriority priority
) {
}
