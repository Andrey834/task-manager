package ru.em.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.em.taskmanager.dto.task.TaskCommentDto;
import ru.em.taskmanager.dto.task.TaskCommentRequest;
import ru.em.taskmanager.dto.task.TaskDto;
import ru.em.taskmanager.dto.task.TaskShortDto;
import ru.em.taskmanager.service.PersonTaskService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Управление задачами - Пользователь")
@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final PersonTaskService personTaskService;

    @Operation(summary = "Завершить выполнение назначенной задачи")
    @PatchMapping("/{taskId}/completed")
    ResponseEntity<TaskDto> updateStatus(Principal principal,
                                         @PathVariable(name = "taskId") Long taskId) {
        return ResponseEntity
                .ok(personTaskService.completedTask(principal.getName(), taskId));
    }

    @Operation(summary = "Оставить комментарий в назначенной задаче")
    @PostMapping("/comment")
    ResponseEntity<TaskCommentDto> addComment(Principal principal,
                                              @RequestBody TaskCommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personTaskService.addComment(principal.getName(), commentRequest));
    }

    @Operation(summary = "Запрос списка назначенных задач в короткой форме с учетом пагинации")
    @GetMapping
    public ResponseEntity<List<TaskShortDto>> getAll(
            Principal principal,
            @RequestParam(name = "from", defaultValue = "0", required = false) @Min(0) int from,
            @RequestParam(name = "size", defaultValue = "10", required = false) @Min(1) @Max(100) int size
    ) {
        return ResponseEntity
                .ok(personTaskService.getAllByEmail(principal.getName(), from, size));
    }

    @Operation(summary = "Запрос назначенной задачи по ID в полной форме")
    @GetMapping("/{taskId}")
    ResponseEntity<TaskDto> getTask(Principal principal,
                                    @PathVariable(name = "taskId") Long taskId) {
        return ResponseEntity
                .ok(personTaskService.getTask(principal.getName(), taskId));
    }


}
