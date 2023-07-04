package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private SessionFactory sessionFactory;

    @Override
    public Optional<User> save(User user) {
        Session session = sessionFactory.openSession();
        Optional<User> userOptional = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            userOptional = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOptional;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.openSession();
        Optional<User> userOptional = Optional.empty();
        try {
            session.beginTransaction();
            userOptional = session.createQuery("FROM User WHERE login = :login AND password = :password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOptional;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.openSession();
        int updatedStrings = 0;
        try {
            session.beginTransaction();
            updatedStrings = session.createQuery("DELETE FROM User WHERE id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return updatedStrings > 0;
    }

    @Override
    public Collection<User> findAll() {
        Session session = sessionFactory.openSession();
        Collection<User> users = List.of();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return users;
    }
}
