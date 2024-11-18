package ru.em.taskmanager.mapper;

import ru.em.taskmanager.dto.task.TaskCommentDto;
import ru.em.taskmanager.dto.task.TaskCommentRequest;
import ru.em.taskmanager.model.TaskComment;

import java.time.LocalDateTime;

public class TaskCommentMapper {

    public static TaskComment toTaskComment(TaskCommentRequest request, Long personId) {
        return TaskComment.builder()
                .taskId(request.taskId())
                .personId(personId)
                .comment(request.comment())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static TaskCommentDto toTaskCommentDto(TaskComment taskComment) {
        return TaskCommentDto.builder()
                .id(taskComment.getId())
                .taskId(taskComment.getTaskId())
                .personId(taskComment.getPersonId())
                .comment(taskComment.getComment())
                .createdAt(taskComment.getCreatedAt())
                .build();
    }
}
