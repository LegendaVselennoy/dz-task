package com.demo.controller;

import com.demo.dto.RequestEditTask;
import com.demo.dto.TaskDTO;
import com.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PatchMapping("patch/{id}")
    public TaskDTO patchTask(@PathVariable Long id,
                             @RequestParam String executor,
                             @RequestBody @Validated RequestEditTask task) {
        return taskService.patchTask(id, task, executor);
    }

    @GetMapping("/{executor}")
    public List<TaskDTO> getTaskByExecutor(@PathVariable String executor,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "1") int pageSize) {
        return taskService.findAllTaskByExecutorService(executor, pageNumber, pageSize);
    }

    @GetMapping
    public List<TaskDTO> getTasksALl() {
        return taskService.findAllTasks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO saveNewTask(@RequestBody @Validated TaskDTO task) {
        return taskService.createNewTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody @Validated TaskDTO task) {
        if (taskService.updateTaskById(id, task).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
    }
}