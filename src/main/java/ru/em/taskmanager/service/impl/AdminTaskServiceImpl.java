package ru.em.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Service;
import ru.em.taskmanager.dto.task.*;
import ru.em.taskmanager.mapper.TaskCommentMapper;
import ru.em.taskmanager.mapper.TaskMapper;
import ru.em.taskmanager.model.Person;
import ru.em.taskmanager.model.Task;
import ru.em.taskmanager.model.TaskComment;
import ru.em.taskmanager.repository.TaskCommentDao;
import ru.em.taskmanager.repository.TaskDao;
import ru.em.taskmanager.service.AdminTaskService;
import ru.em.taskmanager.service.PersonService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTaskServiceImpl implements AdminTaskService {
    private final TaskDao taskDao;
    private final TaskCommentDao taskCommentDao;
    private final PersonService personService;

    @Override
    public TaskDto create(TaskRequest taskRequest, String email) {
        Person person = personService.get(email);
        Task task = TaskMapper.requestToTask(taskRequest, person.getId());
        task = taskDao.save(task);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    @Modifying
    public TaskDto update(Long taskId, UpdateTaskRequest updateTaskRequest, String email) {
        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        Long personId = personService.get(email).getId();

        task = task.toBuilder()
                .initiatorId(personId)
                .executorId(updateTaskRequest.executorId())
                .title(updateTaskRequest.title())
                .description(updateTaskRequest.description())
                .status(updateTaskRequest.status())
                .priority(updateTaskRequest.priority())
                .build();

        Task updateTask = taskDao.save(task);
        return TaskMapper.toTaskDto(updateTask);
    }

    @Override
    public List<TaskShortDto> getAll(int from, int size, Long initiatorId, Long executorId) {
        return taskDao.findAll(from, size, initiatorId, executorId).stream()
                .map(TaskMapper::toTaskShortDto)
                .toList();
    }

    @Override
    public TaskDto get(Long taskId) {
        return taskDao.findById(taskId)
                .map(task -> {
                    List<TaskCommentDto> commentsDto = taskCommentDao.findAllByTaskId(taskId).stream()
                            .map(TaskCommentMapper::toTaskCommentDto)
                            .sorted(Comparator.comparing(TaskCommentDto::id))
                            .toList();

                    TaskDto taskDto = TaskMapper.toTaskDto(task);
                    taskDto = taskDto.toBuilder().comments(commentsDto).build();

                    return taskDto;
                })
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
    }

    @Override
    public void deleteTask(Long taskId) {
        taskDao.deleteById(taskId);
    }

    @Override
    public TaskCommentDto addComment(TaskCommentRequest taskCommentRequest, String email) {
        if (taskDao.existsById(taskCommentRequest.taskId())) {
            Person person = personService.get(email);
            TaskComment taskComment = TaskCommentMapper.toTaskComment(taskCommentRequest, person.getId());
            taskComment = taskCommentDao.save(taskComment);

            return TaskCommentMapper.toTaskCommentDto(taskComment);
        } else {
            throw new IllegalArgumentException("Задача не найдена");
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        taskCommentDao.deleteById(commentId);
    }
}