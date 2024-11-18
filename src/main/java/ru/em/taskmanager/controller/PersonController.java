package ru.em.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.service.PersonService;

import java.security.Principal;

@Tag(name = "Пользователь. Инфо")
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("/current")
    public ResponseEntity<PersonDto> getCurrent(Principal principal) {
        return ResponseEntity.ok(personService.getCurrent(principal.getName()));
    }
}
