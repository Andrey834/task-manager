package ru.em.taskmanager.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.em.taskmanager.model.TaskComment;

import java.util.List;

public interface TaskCommentDao extends ListCrudRepository<TaskComment, Long> {

    List<TaskComment> findAllByTaskId(Long taskId);
}
