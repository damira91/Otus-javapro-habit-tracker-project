package ru.otus.kudaiberdieva.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.CategoryRepository;
import ru.otus.kudaiberdieva.repositories.HabitRepository;
import ru.otus.kudaiberdieva.repositories.UserRepository;


import java.util.List;
import java.util.Optional;


@Service
public class HabitService {

 private final CategoryRepository categoryRepository;
 private final HabitRepository habitRepository;
 private final UserRepository userRepository;


    @Autowired
    public HabitService(CategoryRepository categoryRepository, HabitRepository habitRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public Habit createHabit(Long clientId, Habit habit, String categoryName){
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + clientId + " not found"));

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category" + categoryName + " not found"));

        habit.setUser(user);
        habit.setCategory(category);

        return habitRepository.save(habit);
    }

    public Habit getHabitByIdAndCategory(Long categoryId, Long habitId){
        Optional<Habit> habitOptional = habitRepository.findByIdAndCategoryId(habitId, categoryId);

        // Check if the habit exists
        if (habitOptional.isPresent()) {
            return habitOptional.get();
        } else {
            throw new ResourceNotFoundException("Привычка с идентификатором " + habitId + " и категорией " + categoryId + " не найдена");
        }
    }
    public List<Habit> getHabitsByUserId(Long userId) {
        return habitRepository.findAllByUserId(userId);
    }

    public Optional<Habit> getHabitById(Long habitId) {
        return habitRepository.findById(habitId)
                .map(Optional::of)
                .orElseThrow(() -> new ResourceNotFoundException("Привычка с идентификатором " + habitId + " не найдена."));
    }

    public Habit updateHabit(Long userId, Long habitId, Habit habitDetails) {
        Habit habit = habitRepository.findByIdAndUserId(habitId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found"));

        habit.setName(habitDetails.getName());
        habit.setGoal(habitDetails.getGoal());
        habit.setCategory(habitDetails.getCategory());

        return habitRepository.save(habit);
    }

    public void deleteHabit(Long userId, Long habitId) {
        Habit habit = habitRepository.findByIdAndUserId(habitId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Привычка с идентификатором " + habitId + " не найдена для пользователя"));
        habitRepository.delete(habit);
    }
}
