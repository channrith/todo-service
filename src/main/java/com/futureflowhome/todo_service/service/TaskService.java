package com.futureflowhome.todo_service.service;

import com.futureflowhome.todo_service.dto.CreateTaskRequest;
import com.futureflowhome.todo_service.dto.TaskResponse;
import com.futureflowhome.todo_service.entity.Task;
import com.futureflowhome.todo_service.exception.ListNotFoundException;
import com.futureflowhome.todo_service.entity.TaskStatus;
import com.futureflowhome.todo_service.entity.TodoList;
import com.futureflowhome.todo_service.repository.TaskRepository;
import com.futureflowhome.todo_service.repository.TodoListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TodoListRepository todoListRepository;

    public TaskService(TaskRepository taskRepository, TodoListRepository todoListRepository) {
        this.taskRepository = taskRepository;
        this.todoListRepository = todoListRepository;
    }

    @Transactional
    public TaskResponse createTask(UUID userId, CreateTaskRequest request) {
        TodoList list = todoListRepository.findByIdAndUserId(request.getListId(), userId)
                .orElseThrow(() -> new ListNotFoundException("Todo list not found or access denied: " + request.getListId()));

        Task task = new Task();
        task.setList(list);
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        task.setStatus(TaskStatus.TODO);
        task.setPriority(request.getPriority() != null ? request.getPriority() : 0);
        task.setDueDate(request.getDueDate());

        task = taskRepository.save(task);
        return TaskResponse.from(task);
    }
}
