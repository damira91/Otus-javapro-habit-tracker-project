package ru.otus.kudaiberdieva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kudaiberdieva.dtos.PracticeDto;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.Practice;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;

import ru.otus.kudaiberdieva.services.HabitService;
import ru.otus.kudaiberdieva.services.PracticeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/practices")
public class PracticeController {

    private PracticeService practiceService;
    private HabitService habitService;

    @Autowired
    public PracticeController(PracticeService practiceService, HabitService habitService) {
        this.practiceService = practiceService;
        this.habitService = habitService;
    }

    @PostMapping(path = "")
    public PracticeDto createPractice(@RequestHeader Long habitId, @RequestBody PracticeDto practiceDto) {
        Optional<Habit> habitOptional = habitService.getHabitById(habitId);

        if (habitOptional.isPresent()) {
            Habit habit = habitOptional.get();
            Practice practice = PracticeDto.dtoToEntity(practiceDto, habit);
            Practice createdPractice = practiceService.createPractice(habitId, practice);
            return PracticeDto.entityToDto(createdPractice);
        } else {
            throw new ResourceNotFoundException("Привычка с id " + habitId + " не найдена.");
        }
    }

    @GetMapping(path = "")
    public List<PracticeDto> getPracticesByHabitId(@RequestHeader Long habitId) {
        List<Practice> practices = practiceService.getPracticesByHabitId(habitId);
        return practices.stream()
                .map(PracticeDto::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/date/{date}/")
    public List<PracticeDto> getPracticesByDate(
            @PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return practiceService.getPracticesByDate(date)
                .stream()
                .map(PracticeDto::entityToDto)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/{practiceId}")
    public PracticeDto updatePractice(@RequestHeader Long habitId, @PathVariable Long practiceId, @RequestBody PracticeDto practiceDto) throws IllegalAccessException {
        Habit habit = habitService.getHabitById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit not found"));
        Practice practice = PracticeDto.dtoToEntity(practiceDto, habit);
        practice.setId(practiceId);
        return PracticeDto.entityToDto(practiceService.updatePractice(habitId, practiceId, practice));
    }

    @DeleteMapping(path = "/{practiceId}")
    public ResponseEntity<Void> deletePractice(@RequestHeader Long habitId, @PathVariable Long practiceId) {
        practiceService.deletePractice(habitId, practiceId);
        return ResponseEntity.noContent().build();
    }
}
