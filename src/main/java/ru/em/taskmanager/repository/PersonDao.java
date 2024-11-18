package ru.em.taskmanager.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.em.taskmanager.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao extends ListCrudRepository<Person, Long> {

    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE person " +
                   "SET enabled = true " +
                   "WHERE id IN (:ids);")
    void enabledPersons(@Param(value = "ids") List<Long> ids);

    @Modifying
    @Query(value = "UPDATE person " +
                   "SET enabled = false " +
                   "WHERE id IN (:ids) " +
                   "AND id NOT IN (SELECT pr.person_id " +
                   "               FROM person_role pr " +
                   "               WHERE pr.role LIKE 'ADMIN');")
    void disabledPersons(@Param(value = "ids") List<Long> ids);
}