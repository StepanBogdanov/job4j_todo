package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;

public interface TaskRepository {

    Collection<Task> findAll();

    Collection<Task> findDone();

    Collection<Task> findNew();
 }
