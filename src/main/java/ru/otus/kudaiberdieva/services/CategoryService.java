package ru.otus.kudaiberdieva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.exceptions.InformationExistException;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());
        if (categoryOptional.isPresent()) {
            throw new InformationExistException("Категория с названием: " + category.getName() + " уже существует.");
        } else {
            return categoryRepository.save(category);
        }
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional;
        } else {
            throw new ResourceNotFoundException("Категория с идентификатором: " + categoryId + " не найдена.");
        }
    }

    public Optional<Category> getCategoryByName(String name) {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional.isPresent()) {
            return categoryOptional;
        } else {
            throw new ResourceNotFoundException("Категория с названием: " + name + " не найдена.");
        }
    }

    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            if (category.getName() != null && category.getName().equals(categoryOptional.get().getName())) {
                throw new InformationExistException("Категория с названием " + category.getName() + " уже существует.");
            }
            if (category.getName() != null && !categoryOptional.get().getName().equals(category.getName())) {
                categoryOptional.get().setName(category.getName());
            }
            return categoryRepository.save(categoryOptional.get());
        } else {
            throw new ResourceNotFoundException("Категория с идентификатором: " + categoryId + " не найдена.");
        }
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category == null) {
            throw new ResourceNotFoundException("Категория с идентификатором " + categoryId + " не найдена.");
        }

        categoryRepository.deleteById(categoryId);
    }
}
