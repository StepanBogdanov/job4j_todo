package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        Optional<User> userOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            userOptional = Optional.of(user);
        } catch (Exception e) {
        }
        return userOptional;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional("FROM User WHERE login = :login AND password = :password", User.class,
                Map.of("login", login,
                        "password", password));
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.bool("DELETE FROM User WHERE id = :id", Map.of("id", id));
    }

    @Override
    public Collection<User> findAll() {
        return crudRepository.query("FROM User", User.class);
    }
}
