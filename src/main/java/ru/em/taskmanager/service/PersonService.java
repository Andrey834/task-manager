package ru.em.taskmanager.service;

import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.model.Person;

import java.util.List;

public interface PersonService {

    Person save(Person person, boolean admin);

    Person get(String email);

    PersonDto getCurrent(String email);

    List<Person> getAll();

    void enabledPersons(List<Long> ids);

    void disabledPersons(List<Long> ids);
}
