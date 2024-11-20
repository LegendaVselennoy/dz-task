package com.demo.repository;

import com.demo.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;
    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setExecutor("executor@mail.com");
        repository.save(task);
    }

    @Test
    void saveTaskTest() {
        Task savedTask = repository.save(task);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isGreaterThan(0);
    }

    @Test
    void findTaskByExecutor() {
        List<Task> taskDBList = repository
                .findAllTasksByExecutor(task.getExecutor(), PageRequest.of(0, 1));

        assertThat(taskDBList).isNotNull();
        assertThat(taskDBList.size()).isEqualTo(1);
    }

    @Test
    void taskDeleteTest() {
        repository.delete(task);
        Optional<Task> taskDB = repository.findById(task.getId());

        assertThat(taskDB).isEmpty();
    }

}