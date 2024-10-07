package ru.otus.kudaiberdieva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kudaiberdieva.dtos.CreateHabitDto;
import ru.otus.kudaiberdieva.dtos.HabitDto;
import ru.otus.kudaiberdieva.dtos.HabitProgressDto;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.HabitProgress;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.services.CategoryService;
import ru.otus.kudaiberdieva.services.HabitProgressService;
import ru.otus.kudaiberdieva.services.HabitService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private HabitService habitService;
    private CategoryService categoryService;
    private HabitProgressService habitProgressService;

    @Autowired
    public HabitController(HabitService habitService, CategoryService categoryService, HabitProgressService habitProgressService) {
        this.habitService = habitService;
        this.categoryService = categoryService;
        this.habitProgressService = habitProgressService;
    }

    @GetMapping
    public ResponseEntity<List<HabitDto>> getHabits(@RequestHeader Long clientId) {
        List<Habit> habits = habitService.getHabitsByUserId(clientId);
        if (habits.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<HabitDto> habitDtos = habits.stream()
                .map(HabitDto::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(habitDtos);
    }

    @GetMapping("/progress")
    public ResponseEntity<HabitProgressDto> getHabitProgress(@RequestHeader Long habitId) {
        HabitProgress habitProgress = habitProgressService.calculateProgressForHabit(habitId);
        HabitProgressDto habitProgressDto = HabitProgressDto.entityToDto(habitProgress);
        return ResponseEntity.ok(habitProgressDto);
    }

    @PostMapping
    public ResponseEntity<HabitDto> createHabit(@RequestHeader Long clientId, @RequestBody CreateHabitDto createHabitDto) {
        Habit newHabit = new Habit();
        newHabit.setName(createHabitDto.getName());
        newHabit.setGoal(createHabitDto.getGoal());
        Habit savedHabit = habitService.createHabit(clientId, newHabit, createHabitDto.getCategoryName());
        HabitDto habitDto = HabitDto.entityToDto(savedHabit);
        return new ResponseEntity<>(habitDto, HttpStatus.CREATED);
    }

    @PutMapping("/{habitId}")
    public HabitDto updateHabit(@RequestHeader Long clientId, @PathVariable Long habitId, @RequestBody CreateHabitDto createHabitDto) {
        Habit existingHabit = habitService.getHabitById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Привычка с идентификатором " + habitId + " не найдена"));
        existingHabit.setName(createHabitDto.getName());
        existingHabit.setGoal(createHabitDto.getGoal());
        Category category = categoryService.getCategoryByName(createHabitDto.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Категория " + createHabitDto.getCategoryName() + " не найдена"));
        existingHabit.setCategory(category);
        Habit updatedHabit = habitService.updateHabit(clientId, habitId, existingHabit);
        return HabitDto.entityToDto(updatedHabit);
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit(@RequestHeader Long clientId, @PathVariable Long habitId) {
        habitService.deleteHabit(clientId, habitId);
        return ResponseEntity.noContent().build();
    }
}
