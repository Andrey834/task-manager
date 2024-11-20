package ru.em.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.em.taskmanager.dto.task.*;
import ru.em.taskmanager.enums.TaskPriority;
import ru.em.taskmanager.enums.TaskStatus;
import ru.em.taskmanager.service.AdminTaskService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AdminTaskControllerTest {
    @Mock
    private AdminTaskService adminTaskService;
    @InjectMocks
    private AdminTaskController adminTaskController;
    private final String email = "test@email.com";

    @Test
    void create() {
        TaskRequest taskRequest = new TaskRequest(
                1L,
                "title",
                "description",
                TaskPriority.NORMAL
        );

        TaskDto expectedTaskDto = TaskDto.builder()
                .executorId(taskRequest.executorId())
                .title(taskRequest.title())
                .description(taskRequest.description())
                .status(TaskStatus.IN_WAITING)
                .priority(taskRequest.priority())
                .build();

        when(adminTaskService.create(taskRequest, email)).thenReturn(expectedTaskDto);

        ResponseEntity<TaskDto> response = adminTaskController.create(() -> email, taskRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode(),
                "AdminTaskController.create() Должен вернуть 200(OK)");
        assertEquals(expectedTaskDto, response.getBody(),
                "AdminTaskController.create() Полученный объект не соответствует ожидаемому");
    }

    @Test
    void update() {
        Long taskId = 1L;

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(
                taskId,
                "Update task",
                "update description",
                TaskStatus.IN_PROGRESS,
                TaskPriority.NORMAL
        );

        TaskDto expectedTaskDto = TaskDto.builder()
                .executorId(updateTaskRequest.executorId())
                .title(updateTaskRequest.title())
                .description(updateTaskRequest.description())
                .status(updateTaskRequest.status())
                .priority(updateTaskRequest.priority())
                .build();

        when(adminTaskService.update(taskId, updateTaskRequest, email)).thenReturn(expectedTaskDto);

        ResponseEntity<TaskDto> response = adminTaskController.update(() -> email, taskId, updateTaskRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "AdminTaskController.update() Должен вернуть 200(OK)");
        assertEquals(expectedTaskDto, response.getBody(),
                "AdminTaskController.update() Полученный объект не соответствует ожидаемому");

    }

    @Test
    void getAll() {
        Long initiatorId = 1L;
        Long executorId = 2L;
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

        when(adminTaskService.getAll(from, size, initiatorId, executorId))
                .thenReturn(expectedList);

        ResponseEntity<List<TaskShortDto>> response = adminTaskController.getAll(from, size, initiatorId, executorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void get() {
        Long taskId = 1L;
        TaskDto exepctedTaskDto = TaskDto.builder().id(taskId).build();

        when(adminTaskService.get(taskId)).thenReturn(exepctedTaskDto);

        ResponseEntity<TaskDto> response = adminTaskController.get(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exepctedTaskDto, response.getBody());
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;
        ResponseEntity<Void> response = adminTaskController.deleteTask(taskId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void addComment() {
        Long taskId = 1L;
        String email = "test@email.com";
        TaskCommentRequest commentRequest = new TaskCommentRequest(taskId, "coment");
        TaskCommentDto expectedTaskCommentDro = TaskCommentDto.builder().taskId(taskId).build();

        when(adminTaskService.addComment(commentRequest, email)).thenReturn(expectedTaskCommentDro);

        ResponseEntity<TaskCommentDto> response = adminTaskController.addComment(() -> email, commentRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTaskCommentDro, response.getBody());
    }

    @Test
    void deleteComment() {
        Long commentId = 1L;
        ResponseEntity<Void> response = adminTaskController.deleteComment(commentId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}