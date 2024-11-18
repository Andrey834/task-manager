package ru.em.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Объект для добавления нового комментария к задаче")
public record TaskCommentRequest(
        @Schema(description = "ID задачи к которой добавляется текущий комментарий(Person.id)")
        @NotBlank(message = "Номер задачи является обязательным для заполнения")
        Long taskId,
        @Schema(description = "Текст комментария. Количество символов для комментария: от 5 до 500")
        @NotBlank(message = "Комментарий является обязательным для заполнения")
        @Length(min = 6, max = 500, message = "Количество символов для комментария: от 5 до 500")
        String comment
) {
}
