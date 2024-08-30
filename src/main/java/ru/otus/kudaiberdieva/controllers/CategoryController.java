package ru.otus.kudaiberdieva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Practice;
import ru.otus.kudaiberdieva.services.CategoryService;
import ru.otus.kudaiberdieva.services.PracticeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;
    private PracticeService practiceService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setPracticeService(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @PostMapping(path = "/categories/")
    public Category createCategory(@RequestBody Category categoryObj) {
        return categoryService.createCategory(categoryObj);
    }

    @GetMapping(path = "/categories/{categoryId}/")
    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping(path = "/categories/")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping(path = "/categories/{categoryId}")
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }

    @DeleteMapping(path = "/categories/{categoryId}/")
    public void deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PostMapping(path = "/habits/{habitId}/practices/")
    public Practice createPractice(@PathVariable(value = "habitId") Long habitId, @RequestBody Practice practice) {
        return practiceService.createPractice(habitId, practice);
    }

    @GetMapping(path = "/practices/{habitId}/")
    public Practice getPracticeById(@PathVariable(value = "habitId") Long habitId) {
        return practiceService.getPracticeById(habitId);
    }

    @GetMapping(path = "/practices/date/{date}/")
    public List<Practice> getPracticeByDate(@PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return practiceService.getPracticeByDate(date);
    }

    @GetMapping(path = "/practices/")
    public List<Practice> getAllPractices() {
        return practiceService.getAllPractices();
    }


    @PutMapping(path = "/practices/{practiceId}")
    public Practice updatePractice(@PathVariable(value = "practiceId") Long practiceId, @RequestBody Practice practice) throws IllegalAccessException {
        practice.setId(practiceId);
        return practiceService.updatePractice(practice);
    }

    @DeleteMapping(path = "/practices/{practiceId}/")
    public void deletePractice(@PathVariable(value = "practiceId") Long practiceId) {
        practiceService.deletePractice(practiceId);
    }
}
