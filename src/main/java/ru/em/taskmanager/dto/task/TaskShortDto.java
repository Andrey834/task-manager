package ru.em.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.em.taskmanager.enums.TaskPriority;
import ru.em.taskmanager.enums.TaskStatus;

@Schema(description = "Задача в короткой форме(без описания и комментарий)")
@Builder
public record TaskShortDto(
        @Schema(description = "ID комментария")
        Long id,
        @Schema(description = "ID инициатора задачи")
        Long initiatorId,
        @Schema(description = "ID исполнителя задачи")
        Long executorId,
        @Schema(description = "Наимеование задачи")
        String title,
        @Schema(description = "Статус задачи")
        TaskStatus status,
        @Schema(description = "Приоритет задачи")
        TaskPriority priority
) {
}
