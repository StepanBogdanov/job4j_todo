package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Collection<Task> findAll();

    Collection<Task> findDone();

    Collection<Task> findNew();

    Task save(Task task);

    Optional<Task> findById(int id);

    boolean done(Task task);

    boolean update(Task task);

    boolean delete(int id);
 }
