package ru.em.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.mapper.PersonMapper;
import ru.em.taskmanager.service.AdminPersonService;
import ru.em.taskmanager.service.PersonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPersonServiceImpl implements AdminPersonService {
    private final PersonService personService;

    @Override
    public List<PersonDto> getPersons() {
        return personService.getAll().stream()
                .map(PersonMapper::toPersonDto)
                .toList();
    }

    @Override
    public void enabledPersons(List<Long> ids) {
        personService.enabledPersons(ids);
    }

    @Override
    public void disabledPersons(List<Long> ids) {
        personService.disabledPersons(ids);
    }
}
