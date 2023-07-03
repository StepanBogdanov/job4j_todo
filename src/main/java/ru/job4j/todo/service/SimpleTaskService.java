package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Collection<Task> findDone() {
        return taskRepository.findDone();
    }

    @Override
    public Collection<Task> findNew() {
        return taskRepository.findNew();
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public boolean done(int id) {
        return taskRepository.done(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public boolean delete(int id) {
        return taskRepository.delete(id);
    }
}
