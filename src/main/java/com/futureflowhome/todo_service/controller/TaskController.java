package com.futureflowhome.todo_service.controller;

import com.futureflowhome.todo_service.dto.CreateTaskRequest;
import com.futureflowhome.todo_service.dto.TaskResponse;
import com.futureflowhome.todo_service.security.JwtPrincipalUtils;
import com.futureflowhome.todo_service.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody @Valid CreateTaskRequest request, Authentication authentication) {
        UUID userId = JwtPrincipalUtils.getUserId(authentication);
        if (userId == null) {
            throw new IllegalStateException("User ID not found in JWT");
        }
        return taskService.createTask(userId, request);
    }
}
