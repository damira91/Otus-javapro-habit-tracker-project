package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HabitDto {
    private Long id;
    private String name;
    private String outcome;
    private Integer progress;
    private String categoryName;

    public HabitDto convertToDto(Habit habit) {
        return new HabitDto(
                habit.getId(),
                habit.getName(),
                habit.getOutcome(),
                habit.getProgress(),
                habit.getCategory() != null ? habit.getCategory().getName() : null
        );
    }

    public Habit convertToEntity(HabitDto habitDto, User user, Category category) {
        return new Habit(
                habitDto.getId(),
                habitDto.getName(),
                habitDto.getOutcome(),
                habitDto.getProgress(),
                user,
                category,
                null // practices can be set later
        );
    }
}
