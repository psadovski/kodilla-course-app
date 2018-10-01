package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Content 1");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals((Long)1L, task.getId());
        assertEquals("Task 1", task.getTitle());
        assertEquals("Content 1", task.getContent());
    }

    @Test
    public void testMapToTaskWhenTaskDtoIsNull() {
        //Given
        TaskDto taskDto = null;

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertNull(task);
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "Task 1", "Content 1");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals((Long)1L, taskDto.getId());
        assertEquals("Task 1", taskDto.getTitle());
        assertEquals("Content 1", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoWhenTaskIsNull() {
        //Given
        Task task = null;

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertNull(taskDto);
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Title 1", "Content 1"));

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(1, taskDtoList.size());
        assertEquals((Long)1L, taskDtoList.get(0).getId());
        assertEquals("Title 1", taskDtoList.get(0).getTitle());
        assertEquals("Content 1", taskDtoList.get(0).getContent());
    }

    public void testMapToTaskDtoListWhenTaskListIsNull() {
        //Given
        List<Task> taskList = null;

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertNull(taskDtoList);
    }
}
