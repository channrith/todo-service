package com.futureflowhome.todo_service.service;

import com.futureflowhome.todo_service.dto.CreateTodoListRequest;
import com.futureflowhome.todo_service.dto.TodoListResponse;
import com.futureflowhome.todo_service.dto.UpdateTodoListRequest;
import com.futureflowhome.todo_service.entity.TodoList;
import com.futureflowhome.todo_service.exception.ListNotFoundException;
import com.futureflowhome.todo_service.repository.TodoListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;

    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Transactional
    public TodoListResponse create(UUID userId, CreateTodoListRequest request) {
        TodoList list = new TodoList();
        list.setUserId(userId);
        list.setName(request.getName().trim());
        list.setDescription(trimToNull(request.getDescription()));
        list = todoListRepository.save(list);
        return TodoListResponse.from(list);
    }

    @Transactional(readOnly = true)
    public List<TodoListResponse> listAll(UUID userId) {
        return todoListRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(TodoListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TodoListResponse getById(UUID userId, Long listId) {
        TodoList list = todoListRepository.findByIdAndUserId(listId, userId)
                .orElseThrow(() -> new ListNotFoundException("Todo list not found or access denied: " + listId));
        return TodoListResponse.from(list);
    }

    @Transactional
    public TodoListResponse update(UUID userId, Long listId, UpdateTodoListRequest request) {
        TodoList list = todoListRepository.findByIdAndUserId(listId, userId)
                .orElseThrow(() -> new ListNotFoundException("Todo list not found or access denied: " + listId));
        list.setName(request.getName().trim());
        list.setDescription(trimToNull(request.getDescription()));
        list = todoListRepository.save(list);
        return TodoListResponse.from(list);
    }

    @Transactional
    public void delete(UUID userId, Long listId) {
        TodoList list = todoListRepository.findByIdAndUserId(listId, userId)
                .orElseThrow(() -> new ListNotFoundException("Todo list not found or access denied: " + listId));
        todoListRepository.delete(list);
    }

    private static String trimToNull(String s) {
        if (s == null) {
            return null;
        }
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
