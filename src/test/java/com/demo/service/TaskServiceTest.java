package com.demo.service;

import com.demo.dto.TaskDTO;
import com.demo.entity.Task;
import com.demo.entity.enumerate.Priority;
import com.demo.entity.enumerate.Status;
import com.demo.mapper.TaskMapper;
import com.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repository;
    @InjectMocks
    private TaskService service;
    @Mock
    private TaskMapper taskMapper;
    private TaskDTO dto;
    private Task task;

    @BeforeEach
    public void setUp() {
        dto = TaskDTO.builder()
                .heading("heading")
                .description("description")
                .status(Status.WAITING)
                .priority(Priority.MEDIUM)
                .comment("This is a comment for the task")
                .executor("Executor")
                .build();
        task = new Task();
    }

    @Test
    void taskUpdateTest() {
        Long taskId = 1L;
        given(repository.findById(taskId)).willReturn(Optional.of(task));
        when(taskMapper.taskDtoToTask(any(TaskDTO.class))).thenReturn(task);
        when(repository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.taskToTaskDTO(task)).thenReturn(dto);

        Optional<TaskDTO> updateTask = service.updateTaskById(taskId, dto);

        assertThat(updateTask).isPresent();
        assertThat(updateTask.get().description()).isEqualTo(dto.description());
    }

    @Test
    void findTaskAllTest() {
        Task secondTask = new Task();

        when(repository.findAll()).thenReturn(List.of(task, secondTask));
        when(taskMapper.taskToTaskDTO(task)).thenReturn(dto);

        List<TaskDTO> result = service.findAllTasks();

        assertThat(result).hasSize(2);
    }
}