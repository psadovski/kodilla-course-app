package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void testTaskDtoToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Content 1");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertThat(taskDto).isEqualToComparingFieldByField(task);
    }

    @Test
    public void testTaskDtoToTaskWhenTaskDtoIsNull() {
        //Given
        TaskDto taskDto = null;

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertNull(task);
    }

    @Test
    public void testTaskToTaskDto() {
        //Given
        Task task = new Task(1L, "Task 1", "Content 1");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertThat(task).isEqualToComparingFieldByField(taskDto);
    }

    @Test
    public void testTaskToTaskDtoWhenTaskIsNull() {
        //Given
        Task task = null;

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertNull(taskDto);
    }

    @Test
    public void testTaskListToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Title 1", "Content 1"));

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(1, taskDtoList.size());
        assertThat(taskDtoList.equals(Arrays.asList(new Task(1L, "Title 1", "Content 1")))).isTrue();
    }

    public void testTaskListToTaskDtoListWhenTaskListIsNull() {
        //Given
        List<Task> taskList = null;

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertNull(taskDtoList);
    }
}
