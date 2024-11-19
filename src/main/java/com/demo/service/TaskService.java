package com.demo.service;

import com.demo.dto.RequestEditTask;
import com.demo.dto.TaskDTO;
import com.demo.entity.Task;
import com.demo.exception.NoEditAccessException;
import com.demo.exception.ResourceNotFondException;
import com.demo.mapper.TaskMapper;
import com.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final static String TASK_NOT_FOUND = "Task not found";
    private final static String NO_EDITING_RIGHTS= "No editing rights";
    private final static String TASK_WITH_ID_NOT_FOUND = "Task with id [%s] not found";

    public TaskDTO patchTask(Long id, RequestEditTask requestEditTask, String executor) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFondException(TASK_NOT_FOUND));
        if (task.getExecutor().equals(executor)) {
            task.setStatus(requestEditTask.status());
            task.setComment(requestEditTask.comment());
        } else {
            throw new NoEditAccessException(NO_EDITING_RIGHTS);
        }
        return taskMapper.taskToTaskDTO(taskRepository.save(task));
    }

    public List<TaskDTO> findAllTaskByExecutorService(String executor, int pageNumber, int pageSize) {
        return taskRepository.findAllTasksByExecutor(executor, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(taskMapper::taskToTaskDTO)
                .toList();
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFondException(TASK_WITH_ID_NOT_FOUND.formatted(id));
        }
        taskRepository.deleteById(id);
    }

    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::taskToTaskDTO)
                .toList();
    }

    public Optional<TaskDTO> updateTaskById(Long id, TaskDTO task) {
        if (taskRepository.findById(id).isPresent()) {
            TaskDTO foundTask = TaskDTO.builder()
                    .heading(task.heading())
                    .description(task.description())
                    .comment(task.comment())
                    .status(task.status())
                    .priority(task.priority())
                    .executor(task.executor())
                    .build();
            return Optional.of(taskMapper.taskToTaskDTO(
                    taskRepository.save(taskMapper.taskDtoToTask(foundTask))
            ));
        } else {
            return Optional.empty();
        }
    }

    public TaskDTO createNewTask(TaskDTO task) {
        return taskMapper.taskToTaskDTO(
                taskRepository.save(taskMapper.taskDtoToTask(task))
        );
    }
}