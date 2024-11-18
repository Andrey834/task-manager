package ru.em.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.service.AdminPersonService;

import java.util.List;

@Tag(name = "Управление пользователями. Администратор")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/person")
public class AdminPersonController {
    private final AdminPersonService adminPersonService;

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        return ResponseEntity.ok(adminPersonService.getPersons());
    }

    @Operation(summary = "Разблокировать пользователей")
    @PostMapping("/enabled")
    public ResponseEntity<Void> enabledPersons(
            @RequestBody @Parameter(description = "Список ID пользователей для разрешения доступа") List<Long> ids) {

        adminPersonService.enabledPersons(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Заблокировать пользователей")
    @PostMapping("/disabled")
    public ResponseEntity<Void> disabledPersons(
            @RequestBody @Schema(description = "Список ID пользователей для запрета доступа") List<Long> ids) {

        adminPersonService.disabledPersons(ids);
        return ResponseEntity.ok().build();
    }
}
