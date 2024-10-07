package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.User;

import java.time.Instant;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class HabitDto {
    private Long id;
    private String name;
    private String goal;
    private String categoryName;

    public static HabitDto entityToDto(Habit habit) {
        HabitDto dto = new HabitDto();
        dto.setId(habit.getId());
        dto.setName(habit.getName());
        dto.setGoal(habit.getGoal());
        dto.setCategoryName(habit.getCategory().getName());
        return dto;
    }
}