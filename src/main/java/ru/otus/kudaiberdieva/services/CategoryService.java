package ru.otus.kudaiberdieva.services;

import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.User;


import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategory(Long categoryId);
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(Long categoryId);
    Category createCategory(Category categoryObject);
    Optional<Habit> getHabitById(Long habitId);
}
