package com.demo.controller;

import com.demo.dto.TaskDTO;
import com.demo.entity.Task;
import com.demo.entity.enumerate.Priority;
import com.demo.entity.enumerate.Status;
import com.demo.mapper.TaskMapper;
import com.demo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    private static final String TASK_PATH = "/task/";
    private static final String USERNAME = "admin@yan.com";
    private static final String PASSWORD = "password";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    TaskService service;
    @Mock
    TaskMapper taskMapper;
    @Autowired
    WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testCreateTask() throws Exception {
        Task dto1 = new Task(
                1L,
                "heaad",
                "descrip",
                Status.PROCESSING,
                Priority.MEDIUM,
                "sldlsa;sc,as,d",
                "Author",
                "Executor"
        );
        TaskDTO dto = taskMapper.taskToTaskDTO(dto1);
        given(service.createNewTask(any(TaskDTO.class))).willReturn(dto);

         ResultActions response = mockMvc.perform(post(TASK_PATH)
                        .with(csrf())
                        .with(user(USERNAME).password(PASSWORD).roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)));

            response
                    .andExpect(status().isCreated())

                    .andExpect(jsonPath("$.heading", is(dto1.getHeading())))
                    .andExpect(jsonPath("$.description", is(dto1.getDescription())))
                    .andExpect(jsonPath("$.comment", is(dto1.getComment())))
                    .andExpect(jsonPath("$.status", is(dto1.getStatus())))
                    .andExpect(jsonPath("$.priority", is(dto1.getPriority())))
                    .andExpect(jsonPath("$.executor", is(dto1.getExecutor())));
    }
}