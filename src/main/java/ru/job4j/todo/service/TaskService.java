package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Collection<Task> findAll();

    Collection<Task> findDone();

    Collection<Task> findNew();

    Task save(Task task);

    Optional<Task> findById(int id);

    boolean update(Task task);

    boolean delete(int id);
}
