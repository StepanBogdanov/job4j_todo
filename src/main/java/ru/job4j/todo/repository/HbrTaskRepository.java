package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbrTaskRepository implements TaskRepository{

    private SessionFactory sessionFactory;

    @Override
    public Collection<Task> findAll() {
        Session session = sessionFactory.openSession();
        List<Task> tasks = List.of();
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task ORDER BY id").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> findDone() {
        Session session = sessionFactory.openSession();
        List<Task> tasks = List.of();
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task WHERE done = true ORDER BY id").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> findNew() {
        Session session = sessionFactory.openSession();
        List<Task> tasks = List.of();
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task WHERE created >= (current_date - 3) ORDER BY id").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Task save(Task task) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sessionFactory.openSession();
        Optional<Task> task = Optional.empty();
        try {
            session.beginTransaction();
            task = session.createQuery("from Task where id = :id")
                    .setParameter("id", id).uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public void update(Task task) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}

