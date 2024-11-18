package ru.em.taskmanager.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.em.taskmanager.model.PersonRole;

import java.util.Set;

public interface PersonRoleDao extends ListCrudRepository<PersonRole, Long> {

    Set<PersonRole> findAllByPersonId(Long id);
}
