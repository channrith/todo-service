package com.futureflowhome.todo_service.repository;

import com.futureflowhome.todo_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
