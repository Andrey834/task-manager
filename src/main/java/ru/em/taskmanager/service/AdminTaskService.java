package ru.em.taskmanager.service;

import ru.em.taskmanager.dto.task.*;

import java.util.List;

public interface AdminTaskService {
    TaskDto create(TaskRequest taskRequest, String email);

    TaskDto update(Long taskId, UpdateTaskRequest updateTaskRequest, String email);

    List<TaskShortDto> getAll(int from, int size, Long initiatorId, Long executorId);

    TaskDto get(Long taskId);

    void deleteTask(Long taskId);

    TaskCommentDto addComment(TaskCommentRequest taskCommentRequest, String email);

    void deleteComment(Long commentId);
}
