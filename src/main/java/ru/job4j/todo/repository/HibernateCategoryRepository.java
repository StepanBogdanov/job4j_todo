package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class);
    }

    @Override
    public Collection<Category> findById(Collection<Integer> listId) {
        return crudRepository.query("FROM Category WHERE id IN :listId", Category.class,
                Map.of("listId", listId));
    }

    @Override
    public Category findById(int id) {
        return crudRepository.optional("FROM Category WHERE id = :catId",
                Category.class, Map.of("catId", id)).get();
    }
}
