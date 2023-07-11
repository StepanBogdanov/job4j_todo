package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query("FROM Priority", Priority.class);
    }
}
