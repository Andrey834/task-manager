package ru.em.taskmanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ru.em.taskmanager.enums.TaskPriority;
import ru.em.taskmanager.enums.TaskStatus;

import java.io.Serial;
import java.io.Serializable;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Long initiatorId;
    private Long executorId;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
