package com.futureflowhome.todo_service.dto;

import com.futureflowhome.todo_service.entity.TodoList;

import java.time.LocalDateTime;

public class TodoListResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TodoListResponse from(TodoList list) {
        TodoListResponse r = new TodoListResponse();
        r.setId(list.getId());
        r.setName(list.getName());
        r.setDescription(list.getDescription());
        r.setCreatedAt(list.getCreatedAt());
        r.setUpdatedAt(list.getUpdatedAt());
        return r;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
