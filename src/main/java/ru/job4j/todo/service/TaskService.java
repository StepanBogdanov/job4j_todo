package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;

public interface TaskService {

    Collection<Task> findAll();

    Collection<Task> findDone();

    Collection<Task> findNew();

    Task save(Task task);
}
