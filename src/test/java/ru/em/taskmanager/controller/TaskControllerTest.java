package ru.em.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.em.taskmanager.dto.task.TaskCommentDto;
import ru.em.taskmanager.dto.task.TaskCommentRequest;
import ru.em.taskmanager.dto.task.TaskDto;
import ru.em.taskmanager.dto.task.TaskShortDto;
import ru.em.taskmanager.enums.TaskPriority;
import ru.em.taskmanager.enums.TaskStatus;
import ru.em.taskmanager.service.PersonTaskService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Mock
    private PersonTaskService personTaskService;
    @InjectMocks
    private TaskController taskController;
    private final String email = "test@test.ru";

    @Test
    void completedTask() {
        Long taskId = 1L;

        TaskDto expectedTaskDto = TaskDto.builder()
                .id(taskId)
                .initiatorId(1L)
                .executorId(2L)
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.COMPLETED)
                .comments(List.of())
                .build();

        when(personTaskService.completedTask(email, taskId)).thenReturn(expectedTaskDto);

        ResponseEntity<TaskDto> response = taskController.completedTask(() -> email, taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTaskDto, response.getBody());
    }

    @Test
    void addComment() {
        Long taskId = 1L;
        Long personId = 1L;
        String textComment = "Необходимо уточнение";
        TaskCommentRequest taskCommentRequest = new TaskCommentRequest(taskId, textComment);

        TaskCommentDto expectedTaskCommentDto = new TaskCommentDto(
                1L, taskId, personId, textComment, LocalDateTime.now());

        when(personTaskService.addComment(email, taskCommentRequest))
                .thenReturn(expectedTaskCommentDto);

        ResponseEntity<TaskCommentDto> response = taskController.addComment(() -> email, taskCommentRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedTaskCommentDto, response.getBody());
    }

    @Test
    void getAll() {
        int from = 0;
        int size = 3;

        TaskShortDto taskShortDto = new TaskShortDto(
                1L,
                1L,
                2L,
                "test",
                TaskStatus.IN_PROGRESS,
                TaskPriority.NORMAL);

        TaskShortDto taskShortDto2 = new TaskShortDto(
                2L,
                1L,
                2L,
                "test",
                TaskStatus.IN_PROGRESS,
                TaskPriority.NORMAL);

        List<TaskShortDto> expectedList = List.of(taskShortDto, taskShortDto2);

        when(personTaskService.getAllByEmail(email, from, size))
                .thenReturn(expectedList);

        ResponseEntity<List<TaskShortDto>> response = taskController.getAll(() -> email, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void getTask() {
        Long taskId = 1L;

        TaskDto expectedTaskDto = new TaskDto(
                taskId,
                1L,
                2L,
                "test",
                "description",
                TaskStatus.IN_PROGRESS,
                TaskPriority.NORMAL,
                List.of()
        );

        when(personTaskService.getTask(email, taskId))
                .thenReturn(expectedTaskDto);

        ResponseEntity<TaskDto> response = taskController.getTask(() -> email, taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTaskDto, response.getBody());
    }
}