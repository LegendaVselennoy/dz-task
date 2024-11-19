package com.demo.repository;

import com.demo.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllTasksByExecutor(String executorEmail, Pageable pageable);
}