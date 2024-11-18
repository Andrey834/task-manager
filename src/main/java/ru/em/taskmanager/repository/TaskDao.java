package ru.em.taskmanager.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.em.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao extends ListCrudRepository<Task, Long> {

    boolean existsById(Long taskId);

    @Query(value = "SELECT * " +
                   "FROM task " +
                   "WHERE (:initiatorId = 0 OR initiator_id = :initiatorId) " +
                   "AND (:executorId = 0 OR executor_id = :executorId) " +
                   "ORDER BY id " +
                   "OFFSET :from " +
                   "LIMIT :size;")
    List<Task> findAll(@Param(value = "from") int from,
                       @Param(value = "size") int size,
                       @Param(value = "initiatorId") Long initiatorId,
                       @Param(value = "executorId") Long executorId);

    Optional<Task> findByExecutorId(Long executorId);

    @Query(value = "SELECT t.* " +
                   "FROM task t " +
                   "JOIN person p ON p.id = t.executor_id " +
                   "WHERE p.email LIKE :email " +
                   "ORDER BY t.id " +
                   "OFFSET :from " +
                   "LIMIT :size;")
    List<Task> findAllByPersonEmail(@Param(value = "from") int from,
                                    @Param(value = "size") int size,
                                    @Param(value = "email") String email);

    @Query(value = "SELECT t.* " +
                   "FROM task t " +
                   "JOIN person p ON p.id = t.executor_id " +
                   "WHERE p.email LIKE :email;")
    Optional<Task> getTaskByIdAndEmail(@Param(value = "email") Long taskId,
                                       @Param(value = "email") String email);

}
