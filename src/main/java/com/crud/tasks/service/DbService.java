package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTask(final Long id) {
        return repository.findById(id);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public Optional<Task> deleteTask(final Long id) {
        return repository.deleteById(id);
    }

    public boolean exist(final Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public Task updateTask(final Long id, final Task task) {
        if (getTask(id).isPresent()) {
            Task updatedTask = getTask(id).get();
            updatedTask.setTitle(task.getTitle());
            updatedTask.setContent(task.getContent());

            return repository.save(updatedTask);
        }

        return null;
    }
}
