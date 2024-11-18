package ru.em.taskmanager.dto;

import java.util.Map;

public record ApiError(
        Map<String, String> errors
) {
}
