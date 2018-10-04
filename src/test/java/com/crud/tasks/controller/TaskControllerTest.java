package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void testGetTasksWithEmptyTaskList() throws Exception {
        //Given
        when(dbService.getAllTasks()).thenReturn(new ArrayList<>());

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetTasks() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Test name", "Test description"));

        when(dbService.getAllTasks()).thenReturn(taskList);
        taskMapper.mapToTaskDtoList(dbService.getAllTasks());

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test name")))
                .andExpect(jsonPath("$[0].content", is("Test description")));
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test name", "Test description");
        TaskDto taskDto = new TaskDto(1L, "Test name", "Test description");

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test name")))
                .andExpect(jsonPath("$.content", is("Test description")));
    }

    @Test
    public void testCreateTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test name", "Test description");
        TaskDto taskDto = new TaskDto(1L, "Test name", "Test description");

        when(dbService.saveTask(task).getId()).thenReturn(1L);

        //When & Then
        mockMvc.perform(post("/v1/trello/create")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }
}
