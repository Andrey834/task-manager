package ru.em.taskmanager.mapper;

import ru.em.taskmanager.dto.task.TaskDto;
import ru.em.taskmanager.dto.task.TaskRequest;
import ru.em.taskmanager.dto.task.TaskShortDto;
import ru.em.taskmanager.enums.TaskStatus;
import ru.em.taskmanager.model.Task;

public class TaskMapper {

    public static Task requestToTask(TaskRequest taskRequest, Long initiatorId) {
        return Task.builder()
                .initiatorId(initiatorId)
                .executorId(taskRequest.executorId())
                .title(taskRequest.title())
                .description(taskRequest.description())
                .status(TaskStatus.IN_WAITING)
                .priority(taskRequest.priority())
                .build();

    }

    public static TaskDto toTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .initiatorId(task.getInitiatorId())
                .executorId(task.getExecutorId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
    }

    public static TaskShortDto toTaskShortDto(Task task) {
        return TaskShortDto.builder()
                .id(task.getId())
                .initiatorId(task.getInitiatorId())
                .executorId(task.getExecutorId())
                .title(task.getTitle())
                .priority(task.getPriority())
                .status(task.getStatus())
                .build();
    }
}
