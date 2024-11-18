package ru.em.taskmanager.service;

import ru.em.taskmanager.dto.PersonDto;

import java.util.List;

public interface AdminPersonService {

    List<PersonDto> getPersons();

    void enabledPersons(List<Long> ids);

    void disabledPersons(List<Long> ids);
}
