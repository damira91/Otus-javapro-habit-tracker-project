package ru.otus.kudaiberdieva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kudaiberdieva.dtos.CategoryDto;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.services.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = CategoryDto.dtoToEntity(categoryDto);
        Category createdCategory = categoryService.createCategory(category);
        return CategoryDto.entityToDto(createdCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);
        return optionalCategory
                .map(category -> ResponseEntity.ok(CategoryDto.entityToDto(category))) // Возвращаем 200 OK с DTO
                .orElse(ResponseEntity.notFound().build()); // Возвращаем 404 Not Found
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories().stream().map(CategoryDto::entityToDto).collect(Collectors.toList());
    }

    @PutMapping("/{categoryId}")
    public CategoryDto updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        Category category = CategoryDto.dtoToEntity(categoryDto);
        Category updatedCategory = categoryService.updateCategory(categoryId, category);
        return CategoryDto.entityToDto(updatedCategory);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
