package ru.em.taskmanager.service;

import ru.em.taskmanager.dto.task.TaskCommentDto;
import ru.em.taskmanager.dto.task.TaskCommentRequest;
import ru.em.taskmanager.dto.task.TaskDto;
import ru.em.taskmanager.dto.task.TaskShortDto;

import java.util.List;

public interface PersonTaskService {

    TaskDto completedTask( String email, Long taskId);

    TaskCommentDto addComment(String email, TaskCommentRequest commentRequest);

    List<TaskShortDto> getAllByEmail(String email, int from, int size);

    TaskDto getTask( String email, Long taskId);
}
