package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Collection<Category> findById(Collection<Integer> listId) {
        return categoryRepository.findById(listId);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id);
    }
}
