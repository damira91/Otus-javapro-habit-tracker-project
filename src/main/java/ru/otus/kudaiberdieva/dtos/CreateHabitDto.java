package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateHabitDto {
    private String name;
    private String goal;
    private String categoryName;
}
