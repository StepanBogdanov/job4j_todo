package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

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
            LocalDateTime boundaryDate = LocalDateTime.now().minusDays(3);
            tasks = session.createQuery("FROM Task WHERE created >= :bdate ORDER BY id")
                    .setParameter("bdate", boundaryDate).list();
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
    public boolean done(Task task) {
        Session session = sessionFactory.openSession();
        int updatedStrings = 0;
        try {
            session.beginTransaction();
            updatedStrings = session.createQuery("UPDATE Task SET done = true WHERE id = :uId")
                    .setParameter("uId", task.getId()).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return  updatedStrings > 0;
    }

    @Override
    public boolean update(Task task) {
        Session session = sessionFactory.openSession();
        int updatedStrings = 0;
        try {
            session.beginTransaction();
            String hql = "UPDATE Task SET title = :uTitle, description = :uDescription, created = :uCreated, done = :uDone WHERE id = :uId";
            updatedStrings = session.createQuery(hql)
                    .setParameter("uTitle", task.getTitle())
                    .setParameter("uDescription", task.getDescription())
                    .setParameter("uCreated", task.getCreated())
                    .setParameter("uDone", task.getDone())
                    .setParameter("uId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return updatedStrings > 0;
    }

    @Override
    public boolean delete(int id) {
        Session session = sessionFactory.openSession();
        int deletedStrings = 0;
        try {
            session.beginTransaction();
            deletedStrings = session.createQuery("DELETE FROM Task WHERE id = :taskId")
                    .setParameter("taskId", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return deletedStrings > 0;
    }
}

