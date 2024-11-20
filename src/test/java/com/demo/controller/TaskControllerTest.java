package com.demo.controller;

import com.demo.dto.RequestEditTask;
import com.demo.dto.TaskDTO;
import com.demo.entity.enumerate.Priority;
import com.demo.entity.enumerate.Status;
import com.demo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    private final static String PATH = "/task";
    private final static String USERNAME = "admin@demo.com";
    private final static String PASSWORD = "password";
    private final static String USER_ROLE = "USER";
    private final static String ADMIN_ROLE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    private TaskDTO dto;

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
    }

    @Test
    void patchTaskTest() throws Exception {
        Long taskId = 1L;
        String executor = "Executor2";
        RequestEditTask edit = new RequestEditTask(Status.WAITING, "This is a modified");

        TaskDTO dtoPatch = TaskDTO
                .builder()
                .executor(executor)
                .status(edit.status())
                .comment(edit.comment())
                .build();

        when(taskService.patchTask(taskId, edit, executor)).thenReturn(dtoPatch);

        ResultActions response = mockMvc.perform(patch(PATH + "/patch/" + taskId)
                .with(csrf())
                .with(user(USERNAME).password(PASSWORD).roles(USER_ROLE))
                .queryParam("executor", executor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(edit)));

        response.andExpect(status().isOk());
    }

    @Test
    void createTaskTest() throws Exception {
        given(taskService.createNewTask(any(TaskDTO.class)))
                .willReturn(dto);

        mockMvc.perform(post(PATH)
                        .with(csrf())
                        .with(user(USERNAME).password(PASSWORD).roles(ADMIN_ROLE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}