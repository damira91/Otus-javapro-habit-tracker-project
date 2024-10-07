package ru.otus.kudaiberdieva.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.Practice;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PracticeDto {
    private boolean done;
    private LocalDate date;

    public static PracticeDto entityToDto(Practice practice) {
        return new PracticeDto(
                practice.isDone(),
                practice.getDate()
        );
    }

    public static Practice dtoToEntity(PracticeDto practiceDto, Habit habit) {
        Practice practice = new Practice();
        practice.setDone(practiceDto.isDone());
        practice.setDate(practiceDto.getDate());
        practice.setHabit(habit); // Привязываем сущность Habit
        return practice;
    }
}