package com.futureflowhome.todo_service.exception;

public class ListNotFoundException extends RuntimeException {

    public ListNotFoundException(String message) {
        super(message);
    }
}
