package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTestSuite {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Spy
    private TaskMapper taskMapper;

    @Test
    public void testGetTasksWithEmptyTaskList() {
        //Given
        //When
        when(dbService.getAllTasks()).thenReturn(new ArrayList<>());
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
        List<TaskDto> tasks = taskController.getTasks();

        //Then
        assertNotNull(tasks);
        assertThat(tasks.get(0)).isEqualToComparingFieldByField(new TaskDto(1L, "Test name", "Test description"));
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test name", "Test description");

        //When
        when(dbService.getTask(eq(1L))).thenReturn(Optional.of(task));
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
        when(dbService.updateTask(eq(1L), any())).thenReturn(task);
        TaskDto actual = taskController.updateTask(1L, taskDto);

        //Then
        assertThat(actual.getId()).isEqualTo(1L);
    }
}
