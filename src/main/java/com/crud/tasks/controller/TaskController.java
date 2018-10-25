package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class TaskController {
    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(dbService.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public boolean deleteTask(@PathVariable Long taskId) {
        dbService.deleteTask(taskId);
        return true;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(dbService.updateTask(taskDto.getId(), taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public Long createTask(@RequestBody TaskDto taskDto) {
        return dbService.saveTask(taskMapper.mapToTask(taskDto)).getId();
    }
}
