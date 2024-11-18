package ru.em.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Service;
import ru.em.taskmanager.dto.task.TaskCommentDto;
import ru.em.taskmanager.dto.task.TaskCommentRequest;
import ru.em.taskmanager.dto.task.TaskDto;
import ru.em.taskmanager.dto.task.TaskShortDto;
import ru.em.taskmanager.enums.TaskStatus;
import ru.em.taskmanager.mapper.TaskCommentMapper;
import ru.em.taskmanager.mapper.TaskMapper;
import ru.em.taskmanager.model.Person;
import ru.em.taskmanager.model.Task;
import ru.em.taskmanager.model.TaskComment;
import ru.em.taskmanager.repository.TaskCommentDao;
import ru.em.taskmanager.repository.TaskDao;
import ru.em.taskmanager.service.PersonService;
import ru.em.taskmanager.service.PersonTaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonTaskServiceImpl implements PersonTaskService {
    private final TaskDao taskDao;
    private final TaskCommentDao taskCommentDao;
    private final PersonService personService;


    @Override
    @Modifying
    public TaskDto completedTask(String email, Long taskId) {
        Person person = personService.get(email);

        Task task = taskDao.findByExecutorId(person.getId()).orElseThrow(
                () -> new IllegalArgumentException("Отсутствует задача с ID: " + person.getId())
        );

        task.setStatus(TaskStatus.COMPLETED);

        return TaskMapper.toTaskDto(taskDao.save(task));
    }

    @Override
    public TaskCommentDto addComment(String email, TaskCommentRequest commentRequest) {
        return taskDao.getTaskByIdAndEmail(commentRequest.taskId(), email)
                .map(task -> {
                    TaskComment taskComment = TaskCommentMapper.toTaskComment(commentRequest, task.getId());
                    taskComment = taskCommentDao.save(taskComment);

                    return TaskCommentMapper.toTaskCommentDto(taskComment);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Override
    public List<TaskShortDto> getAllByEmail(String email, int from, int size) {
        return taskDao.findAllByPersonEmail(from, size, email).stream()
                .map(TaskMapper::toTaskShortDto)
                .toList();
    }

    @Override
    public TaskDto getTask(String email, Long taskId) {
        return taskDao.getTaskByIdAndEmail(taskId, email)
                .map(task -> {
                    TaskDto taskDto = TaskMapper.toTaskDto(task);
                    List<TaskCommentDto> comments = taskCommentDao.findAllByTaskId(taskDto.id()).stream()
                            .map(TaskCommentMapper::toTaskCommentDto)
                            .toList();
                    return taskDto.toBuilder().comments(comments).build();
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }
}
