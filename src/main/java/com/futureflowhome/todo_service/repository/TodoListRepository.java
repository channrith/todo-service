package com.futureflowhome.todo_service.repository;

import com.futureflowhome.todo_service.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    Optional<TodoList> findByIdAndUserId(Long id, UUID userId);

    List<TodoList> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
}
