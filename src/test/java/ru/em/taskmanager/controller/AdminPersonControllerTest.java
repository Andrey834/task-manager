package ru.em.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.repository.PersonDao;
import ru.em.taskmanager.service.AdminPersonService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminPersonControllerTest {
    @Mock
    private AdminPersonService adminPersonService;
    @InjectMocks
    private AdminPersonController adminPersonController;

    @Test
    void getAllPersons() {
        PersonDto person1 = new PersonDto(1L, "person1@mail.ru", true);
        PersonDto person2 = new PersonDto(2L, "person2@mail.ru", true);

        List<PersonDto> expectedList = List.of(person1, person2);
        when(adminPersonService.getPersons()).thenReturn(expectedList);

        ResponseEntity<List<PersonDto>> response = adminPersonController.getAllPersons();

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "adminPersonController.getAllPersons() Должен вернуть 200(OK)");
        assertEquals(expectedList, response.getBody(),
                "adminPersonService.getPersons() Полученный список не соответствует ожидаемому");
    }

    @Test
    void enabledPersons() {
        List<Long> ids = List.of(1L, 2L);
        ResponseEntity<Void> response = adminPersonController.enabledPersons(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "adminPersonController.enabledPersons(List<Long> ids) Должен вернуть 200(OK)");
    }

    @Test
    void disabledPersons() {
        List<Long> ids = List.of(1L, 2L);
        ResponseEntity<Void> response = adminPersonController.disabledPersons(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "adminPersonController.disabledPersons(List<Long> ids) Должен вернуть 200(OK)");
    }
}