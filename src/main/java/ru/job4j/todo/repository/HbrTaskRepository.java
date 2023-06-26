package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;

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
            tasks = session.createQuery("FROM Task").list();
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
            tasks = session.createQuery("FROM Task WHERE done = true").list();
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
            tasks = session.createQuery("FROM Task WHERE created >= (current_date - 3)").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }
}

