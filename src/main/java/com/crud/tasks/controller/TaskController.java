package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(dbService.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(@RequestParam Long id) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.getTask(id).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "delete")
    public boolean deleteTask(@RequestParam Long id) throws NothingToDeleteException {
        if (dbService.exist(id)) {
            dbService.deleteTask(id);
            return true;
        }
           throw new NothingToDeleteException() ;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "update", consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestParam Long id, @RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(dbService.updateTask(id, taskMapper.mapToTask(taskDto)).get());
    }

    @RequestMapping(method = RequestMethod.POST, value = "create", consumes = APPLICATION_JSON_VALUE)
    public Long createTask(@RequestBody TaskDto taskDto) {
        dbService.saveTask(taskMapper.mapToTask(taskDto));
        return taskMapper.mapToTask(taskDto).getId();
    }
}
