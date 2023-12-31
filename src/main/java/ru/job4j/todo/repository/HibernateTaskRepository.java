package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private CrudRepository crudRepository;

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("SELECT DISTINCT t FROM Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories ORDER BY t.id",
                Task.class);
    }

    @Override
    public Collection<Task> findDone() {
        return crudRepository.query("SELECT DISTINCT t FROM Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories WHERE t.done = true ORDER BY t.id",
                Task.class);
    }

    @Override
    public Collection<Task> findNew() {
        return crudRepository.query("SELECT DISTINCT t FROM Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories WHERE t.created >= :bDate ORDER BY t.id",
                Task.class, Map.of("bDate", LocalDateTime.now().minusDays(3)));
    }

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional("from Task t JOIN FETCH t.priority LEFT JOIN FETCH t.categories where t.id = :id",
                Task.class, Map.of("id", id));
    }

    @Override
    public boolean done(int id) {
        return crudRepository.bool("UPDATE Task SET done = true WHERE id = :uId",
                Map.of("uId", id));
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.bool("UPDATE Task SET title = :uTitle, description = :uDescription, created = :uCreated, done = :uDone, priority_id = :uPriority WHERE id = :uId",
                Map.of("uTitle", task.getTitle(),
                        "uDescription", task.getDescription(),
                        "uCreated", task.getCreated(),
                        "uDone", task.getDone(),
                        "uId", task.getId(),
                        "uPriority", task.getPriority()));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.bool("DELETE FROM Task WHERE id = :taskId",
                Map.of("taskId", id));
    }
}

