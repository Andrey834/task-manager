package ru.em.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "Комментарий к задаче")
@Builder
public record TaskCommentDto(
        @Schema(description = "ID комментария(TaskComment.id)")
        Long id,
        @Schema(description = "ID задачи, к которой добавлен комментарий(Task.id)")
        Long taskId,
        @Schema(description = "ID пользователя, который добавил комментарий(Person.id)")
        Long personId,
        @Schema(description = "Текст комментария")
        String comment,
        @Schema(description = "Дата и время создания комментария")
        LocalDateTime createdAt
) {
}
