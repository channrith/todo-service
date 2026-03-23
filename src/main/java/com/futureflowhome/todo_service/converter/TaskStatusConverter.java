package com.futureflowhome.todo_service.converter;

import com.futureflowhome.todo_service.entity.TaskStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

/**
 * Maps idiomatic Java {@link TaskStatus} constants to lowercase PostgreSQL {@code task_status} labels.
 */
@Converter(autoApply = false)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, String> {

    @Override
    public String convertToDatabaseColumn(TaskStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return switch (attribute) {
            case TODO -> "todo";
            case IN_PROGRESS -> "in_progress";
            case COMPLETED -> "completed";
            case ARCHIVED -> "archived";
        };
    }

    @Override
    public TaskStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        String normalized = dbData.trim().toLowerCase();
        return switch (normalized) {
            case "todo" -> TaskStatus.TODO;
            case "in_progress" -> TaskStatus.IN_PROGRESS;
            case "completed" -> TaskStatus.COMPLETED;
            case "archived" -> TaskStatus.ARCHIVED;
            default -> throw new IllegalArgumentException(
                    "Unknown task_status value: '" + dbData + "'. Expected one of: "
                            + Arrays.toString(TaskStatus.values()));
        };
    }
}
