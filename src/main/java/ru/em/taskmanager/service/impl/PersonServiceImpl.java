package ru.em.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.enums.ERole;
import ru.em.taskmanager.exception.PersonNotFoundException;
import ru.em.taskmanager.mapper.PersonMapper;
import ru.em.taskmanager.model.Person;
import ru.em.taskmanager.model.PersonRole;
import ru.em.taskmanager.repository.PersonDao;
import ru.em.taskmanager.repository.PersonRoleDao;
import ru.em.taskmanager.service.PersonService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonDao personDao;
    private final PersonRoleDao personRoleDao;

    @Override
    public Person save(Person person, boolean admin) {
        if (personDao.existsByEmail(person.getEmail())) {
            throw new DuplicateKeyException(person.getEmail() + " уже используется");
        } else {
            final Person newPerson = personDao.save(person);
            if (admin) {
                addAdminRole(newPerson.getId());
            }

            return newPerson;
        }
    }

    @Override
    public Person get(String email) {
        Person person = personDao.findByEmail(email).orElseThrow(
                () -> new PersonNotFoundException("Пользователь не найден")
        );

        Set<PersonRole> roles = personRoleDao.findAllByPersonId(person.getId());
        person.setAuthorities(roles);

        return person;
    }

    @Override
    public PersonDto getCurrent(String email) {
        final Person person = get(email);
        return PersonMapper.toPersonDto(person);
    }

    @Override
    public List<Person> getAll() {
        return personDao.findAll();
    }

    @Override
    public void enabledPersons(List<Long> ids) {
        personDao.enabledPersons(ids);
    }

    @Override
    public void disabledPersons(List<Long> ids) {
        personDao.disabledPersons(ids);
    }

    private void addAdminRole(Long personId) {
        PersonRole role = PersonRole.builder()
                .personId(personId)
                .role(ERole.ADMIN)
                .build();
        personRoleDao.save(role);
    }
}
