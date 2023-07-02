package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> save(User user);

    Optional<User> findUserByLoginAndPassword(String login, String password);
}
