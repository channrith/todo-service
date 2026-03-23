package com.futureflowhome.todo_service.controller;

import com.futureflowhome.todo_service.dto.CreateTodoListRequest;
import com.futureflowhome.todo_service.dto.TodoListResponse;
import com.futureflowhome.todo_service.dto.TodoListsDataResponse;
import com.futureflowhome.todo_service.dto.UpdateTodoListRequest;
import com.futureflowhome.todo_service.security.JwtPrincipalUtils;
import com.futureflowhome.todo_service.service.TodoListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/todo-lists")
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoListResponse> create(
            @Valid @RequestBody CreateTodoListRequest request,
            Authentication authentication) {
        UUID userId = requireUserId(authentication);
        TodoListResponse created = todoListService.create(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoListsDataResponse list(Authentication authentication) {
        UUID userId = requireUserId(authentication);
        return new TodoListsDataResponse(todoListService.listAll(userId));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoListResponse getById(@PathVariable Long id, Authentication authentication) {
        UUID userId = requireUserId(authentication);
        return todoListService.getById(userId, id);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoListResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTodoListRequest request,
            Authentication authentication) {
        UUID userId = requireUserId(authentication);
        return todoListService.update(userId, id, request);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, Authentication authentication) {
        UUID userId = requireUserId(authentication);
        todoListService.delete(userId, id);
    }

    private static UUID requireUserId(Authentication authentication) {
        UUID userId = JwtPrincipalUtils.getUserId(authentication);
        if (userId == null) {
            throw new IllegalStateException("User ID not found in JWT");
        }
        return userId;
    }
}
