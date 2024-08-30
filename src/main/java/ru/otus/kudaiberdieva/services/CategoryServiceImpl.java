package ru.otus.kudaiberdieva.services;

import lombok.RequiredArgsConstructor;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.InformationExistException;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.CategoryRepository;
import ru.otus.kudaiberdieva.repositories.HabitRepository;
import ru.otus.kudaiberdieva.security.MyUserDetails;
import ru.otus.kudaiberdieva.security.SecurityUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static ru.otus.kudaiberdieva.security.SecurityUtils.getCurrentLoggedInUser;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final HabitRepository habitRepository;

    @Override
    public List<Category> getAllCategories(){
        User user = getCurrentLoggedInUser();
        List<Category> categoryList = user.getCategories();
        if (categoryList.isEmpty()){
            throw new ResourceNotFoundException("No categories found for User id " + user.getId());
        }
        return categoryList;
    }
    @Override
    public Category createCategory(Category category) {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByNameAndUserId(category.getName(), getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()) {
            throw new InformationExistException("Category with name " + category.getName() + " already exists.");
        } else {
            category.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(category);
        }
    }
    @Override
    public Optional<Category> getCategory(Long categoryId){
        Optional<Category> categoryOptional = Optional.of(categoryRepository.findByIdAndUserId(categoryId, SecurityUtils.getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()){
            return categoryOptional;
        } else {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found.");
        }
    }
    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()) { // Category found in db
            if (category.getName() != null && category.getName().equals(categoryOptional.get().getName()) &&
                    category.getDescription() != null && category.getDescription().equals(categoryOptional.get().getDescription())) {
                throw new InformationExistException("The category " + category.getName() + " with description " + category.getDescription() + " already exists.");
            } else {
                // updates name if not null and different from original
                if (category.getName() != null && !String.valueOf(categoryOptional.get().getName()).equals(category.getName())) {
                    categoryOptional.get().setName(category.getName());
                }
                // updates name if not null and different from original
                if (category.getDescription() != null && !String.valueOf(categoryOptional.get().getDescription()).equals(category.getDescription())) {
                    categoryOptional.get().setDescription(category.getDescription());
                }
                return categoryRepository.save(categoryOptional.get());
            }
        } else {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found.");
        }
    }
        @Override
        public void deleteCategory(Long categoryId) {
            Long userId = getCurrentLoggedInUser().getId();
            Category category = categoryRepository.findByIdAndUserId(categoryId, userId);

            if (category == null) {
                throw new ResourceNotFoundException("Category with id " + categoryId + " not found.");
            }

            categoryRepository.deleteById(categoryId);
        }
    public Habit createCategoryHabit(Long categoryId, Habit habit){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId()));
        if (categoryOptional.isPresent()) {
            Optional<Habit> habitOptional = Optional.ofNullable(habitRepository.findByName(habit.getName()));
            if (habitOptional.isEmpty()) {
                habit.setCategory(categoryOptional.get());
                habit.setUser(getCurrentLoggedInUser());
                return habitRepository.save(habit);
            } else {
                throw new InformationExistException("Habit " + habit.getName() + " already exists.");
            }
        } else {
            throw new ResourceNotFoundException("Category with id " + categoryId + " not found.");
        }
    }

    public Habit getHabitByIdAndCategory(Long categoryId, Long habitId){
        Optional<Category> categoryOptional = getCategory(categoryId);
        Optional<Habit> habitOptional = categoryOptional
                .get()
                .getHabits()
                .stream()
                .filter(habit -> habit.getId() == habitId).findFirst();
        if(habitOptional.isPresent()) {
            return habitOptional.get();
        } else {
            throw new ResourceNotFoundException("habit with id " + habitId + " not found");
        }
    }

    public Optional<Habit> getHabitById(Long habitId) {
        return habitRepository.findById(habitId)
                .map(Optional::of)
                .orElseThrow(() -> new ResourceNotFoundException("Habit with id " + habitId + " not found."));
    }

    public List<Habit> getAllHabits() {
        User user = getCurrentLoggedInUser();
        List<Habit> habitList = user.getHabits();

        if (habitList.isEmpty()){
            throw new ResourceNotFoundException("No habits found for User id " + user.getId());
        }
        return habitList;
    }

    public Habit updateHabit(Habit habit) throws IllegalAccessException {
        Optional<Habit> habitOptional = habitRepository.findById(habit.getId());
        if (habitOptional.isPresent()){
            try {
                for (Field field : Habit.class.getDeclaredFields()) { // Java Reflection allows to loop through class fields
                    field.setAccessible(true); //make private fields accessible
                    Object newValue = field.get(habit); //assigns the current field's value from habit to newValue
                    Object originalValue = field.get(habitOptional.get()); //assigns the current field's value from habitOptional to originalValue
                    if (newValue != null && !newValue.equals(originalValue)) { //if not null and different from original
                        field.set(habitOptional.get(), newValue);
                    }
                }
                return habitRepository.save(habitOptional.get());
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException("Habit with id " + habit.getId() + " not found.");
        }
    }

    public Optional<Habit> deleteHabit(Long categoryId, Long habitId) {
        Optional<Habit> habitOptional = Optional.ofNullable(getHabitByIdAndCategory(categoryId, habitId));
        if (habitOptional.isPresent()) {
            habitRepository.deleteById(habitId);
            return habitOptional;
        } else {
            throw new ResourceNotFoundException("category with id " + categoryId + " not found");
        }
    }

}
