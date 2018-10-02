package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void testGetAllTasksWithEmptyTaskList() {
        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<Task> fetchedTaskList = dbService.getAllTasks();

        //Then
        assertNotNull(fetchedTaskList);
        assertEquals(0, fetchedTaskList.size());
    }

    @Test
    public void testGetAllTasks() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Description 1"));

        when(repository.findAll()).thenReturn(tasks);

        //When
        List<Task> fetchedTaskList = dbService.getAllTasks();

        //Then
        assertNotNull(fetchedTaskList);
        assertEquals(1, fetchedTaskList.size());
        fetchedTaskList.forEach(taskList -> {
            assertEquals((Long)1L, taskList.getId());
            assertEquals("Task 1", taskList.getTitle());
            assertEquals("Description 1", taskList.getContent());
            });
    }

    @Test
    public void testGetTask() {
        //Given
        Task task = new Task(1L, "Task 1", "Description 1");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> taskById = dbService.getTask(1L);

        //Then
        assertThat(Optional.of(task)).isEqualToComparingFieldByField(taskById);
    }

    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(1L, "Task 1", "Description 1");
        when(repository.save(task)).thenReturn(task);

        //When
        Task savedTask = dbService.saveTask(task);

        //Then
        assertThat(task).isEqualToComparingFieldByField(savedTask);
    }

    @Test
    public void testDeleteTask() {
        //Given
        Task task = new Task(1L, "Task 1", "Description 1");
        when(repository.deleteById(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> deletedTask = dbService.deleteTask(1L);

        //Then
        assertThat(Optional.of(task)).isEqualToComparingFieldByField(deletedTask);
    }

    @Test
    public void testExistTask() {
        //Given
        when(repository.existsById(1L)).thenReturn(true);

        //When
        boolean exist = dbService.exist(1L);

        //Then
        assertTrue(exist);
    }
}
