package com.futureflowhome.todo_service.dto;

import java.util.List;

/**
 * Wraps the collection returned by {@code GET /api/todo-lists}.
 */
public class TodoListsDataResponse {

    private List<TodoListResponse> data;

    public TodoListsDataResponse() {
    }

    public TodoListsDataResponse(List<TodoListResponse> data) {
        this.data = data;
    }

    public List<TodoListResponse> getData() {
        return data;
    }

    public void setData(List<TodoListResponse> data) {
        this.data = data;
    }
}
