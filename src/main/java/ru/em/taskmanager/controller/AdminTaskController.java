package ru.em.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.em.taskmanager.dto.task.*;
import ru.em.taskmanager.service.AdminTaskService;

import java.security.Principal;
import java.util.List;

@Tag(name = "Управление задачами. Администратор")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/task")
public class AdminTaskController {
    private final AdminTaskService adminTaskService;

    @Operation(summary = "Запрос на добавлении новой задачи")
    @PostMapping
    public ResponseEntity<TaskDto> create(Principal principal,
                                          @RequestBody @Valid TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminTaskService.create(taskRequest, principal.getName()));
    }

    @Operation(summary = "Запрос на обновление задачи")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> update(Principal principal,
                                          @PathVariable Long taskId,
                                          @RequestBody @Valid UpdateTaskRequest updateTaskRequest) {
        return ResponseEntity.ok(adminTaskService.update(taskId, updateTaskRequest, principal.getName()));
    }

    @Operation(summary = "Запрос списка задач в короткой форме с учетом пагинации и фильтрации")
    @GetMapping
    public ResponseEntity<List<TaskShortDto>> getAll(
            @RequestParam(name = "from", defaultValue = "0", required = false) @Min(0) int from,
            @RequestParam(name = "size", defaultValue = "10", required = false) @Min(1) @Max(100) int size,
            @RequestParam(name = "initiatorId", defaultValue = "0", required = false) Long initiatorId,
            @RequestParam(name = "executorId", defaultValue = "0", required = false) Long executorId
    ) {
        return ResponseEntity.ok(adminTaskService.getAll(from, size, initiatorId, executorId));
    }

    @Operation(summary = "Запрос задачи по ID в полной форме")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> get(@PathVariable Long taskId) {
        return ResponseEntity.ok(adminTaskService.get(taskId));
    }

    @Operation(summary = "Запрос на удаление задачи по ID")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        adminTaskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Запрос на добавление комментария к задаче")
    @PostMapping("/comment")
    public ResponseEntity<TaskCommentDto> addComment(Principal principal,
                                                     @RequestBody @Valid TaskCommentRequest request) {
        return ResponseEntity.ok(adminTaskService.addComment(request, principal.getName()));
    }

    @Operation(summary = "Запрос на удаление комментария по ID")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        adminTaskService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
