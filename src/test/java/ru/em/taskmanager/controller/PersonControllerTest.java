package ru.em.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.service.PersonService;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @Mock
    private PersonService personService;
    @InjectMocks
    private PersonController personController;

    @Test
    void getCurrent() {
        PersonDto expectedPersonDto = new PersonDto(1L, "email@email.com", true);

        when(personService.getCurrent(expectedPersonDto.email())).thenReturn(expectedPersonDto);

        ResponseEntity<PersonDto> response = personController.getCurrent(new Principal() {
            @Override
            public String getName() {
                return expectedPersonDto.email();
            }
        });

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "personService.getCurrent(Principal principal) Должен вернуть 200(OK)");
        assertEquals(expectedPersonDto, response.getBody(),
                "personService.getCurrent(Principal principal) Полученный объект не соответствует ожидаемому");
    }


}