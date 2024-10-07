package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.HabitProgress;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HabitProgressDto {
    private String habitName;
    private int progress;

    public static HabitProgressDto entityToDto(HabitProgress habitProgress) {
        return new HabitProgressDto(habitProgress.getHabitName(), habitProgress.getProgress());
    }
}

