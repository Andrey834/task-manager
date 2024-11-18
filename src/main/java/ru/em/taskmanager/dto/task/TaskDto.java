package ru.em.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.em.taskmanager.enums.TaskPriority;
import ru.em.taskmanager.enums.TaskStatus;
import java.util.List;

@Schema(description = "Задача в полной форме(с описанием и комментариями)")
@Builder(toBuilder = true)
public record TaskDto(
        @Schema(description = "Идентификатор задачи")
        Long id,
        @Schema(description = "Идентификатор инициатора задачи")
        Long initiatorId,
        @Schema(description = "Идентификатор исполнителя задачи")
        Long executorId,
        @Schema(description = "Наименование задачи")
        String title,
        @Schema(description = "Описание задачи")
        String description,
        @Schema(description = "Статус задачи")
        TaskStatus status,
        @Schema(description = "Приоритет задачи")
        TaskPriority priority,
        @Schema(description = "Список комментарий задачи")
        List<TaskCommentDto> comments
) {
}
