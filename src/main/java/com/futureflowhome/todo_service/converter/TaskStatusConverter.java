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
        // If/else avoids javac-generated synthetic nested class (e.g. TaskStatusConverter$1) that
        // Hibernate 7 can fail to load during converter metadata introspection.
        if (attribute == TaskStatus.TODO) {
            return "todo";
        }
        if (attribute == TaskStatus.IN_PROGRESS) {
            return "in_progress";
        }
        if (attribute == TaskStatus.COMPLETED) {
            return "completed";
        }
        if (attribute == TaskStatus.ARCHIVED) {
            return "archived";
        }
        throw new IllegalStateException("Unhandled TaskStatus: " + attribute);
    }

    @Override
    public TaskStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        String normalized = dbData.trim().toLowerCase();
        if ("todo".equals(normalized)) {
            return TaskStatus.TODO;
        }
        if ("in_progress".equals(normalized)) {
            return TaskStatus.IN_PROGRESS;
        }
        if ("completed".equals(normalized)) {
            return TaskStatus.COMPLETED;
        }
        if ("archived".equals(normalized)) {
            return TaskStatus.ARCHIVED;
        }
        throw new IllegalArgumentException(
                "Unknown task_status value: '" + dbData + "'. Expected one of: "
                        + Arrays.toString(TaskStatus.values()));
    }
}
