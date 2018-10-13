package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTestSuite {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    public void testGetTasksWithEmptyTaskList() {
        //Given
        //When
        when(dbService.getAllTasks()).thenReturn(new ArrayList<>());
        taskMapper.mapToTaskDtoList(dbService.getAllTasks());

        List<TaskDto> tasks = taskController.getTasks();

        //Then
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    public void testGetTasks() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Test name", "Test description"));

        //When
        when(dbService.getAllTasks()).thenReturn(taskList);
        taskMapper.mapToTaskDtoList(dbService.getAllTasks());

        List<TaskDto> tasks = taskController.getTasks();

        //Then
        assertNotNull(tasks);
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test name", "Test description");

        //When
        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        taskMapper.mapToTaskDto(dbService.getTask(1L).orElseThrow(TaskNotFoundException::new));

        TaskDto actual = taskController.getTask(1L);

        //Then
        assertEquals(Optional.of(1L), Optional.of(actual.getId()));
        assertEquals("Test name", actual.getTitle());
        assertEquals("Test description", actual.getContent());
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test name", "Test description");
        //When
        when(dbService.exist(1L)).thenReturn(true);
        when(dbService.deleteTask(1L)).thenReturn(Optional.of(task));

        boolean actual = taskController.deleteTask(1L);

        //Then
        assertTrue(actual);
    }

    @Test
    public void testUpdateTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test name", "Test description");
        Task task = new Task(1L, "Test name", "Test description");

        //When
        when(dbService.updateTask(1L, taskMapper.mapToTask(taskDto))).thenReturn(task);
        taskMapper.mapToTaskDto(dbService.updateTask(1L, taskMapper.mapToTask(taskDto)));

        TaskDto actual = taskController.updateTask(1L, taskDto);

        //Then
        assertThat(actual.getId()).isEqualTo(1L);
    }
}
