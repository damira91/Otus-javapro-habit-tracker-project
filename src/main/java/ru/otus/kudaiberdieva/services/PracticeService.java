package ru.otus.kudaiberdieva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.Practice;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.HabitRepository;
import ru.otus.kudaiberdieva.repositories.PracticeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final HabitRepository habitRepository;

    @Autowired
    public PracticeService(PracticeRepository practiceRepository, HabitRepository habitRepository) {
        this.practiceRepository = practiceRepository;
        this.habitRepository = habitRepository;
    }


    public Practice createPractice(Long habitId, Practice practice) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found"));
        practice.setHabit(habit);
        return practiceRepository.save(practice);
    }

    public List<Practice> getPracticesByHabitId(Long habitId) {
        return practiceRepository.findByHabitId(habitId);
    }

    public List<Practice> getPracticesByDate(LocalDate date) {
        List<Practice> practiceList = practiceRepository.findByDate(date);
        if (practiceList.isEmpty()) {
            throw new ResourceNotFoundException("Не найдено не одной привычки на дату: " + date);
        }
        return practiceList;
    }

    public Practice updatePractice(Long habitId, Long id, Practice updatedPractice) {
        return practiceRepository.findByHabitIdAndId(habitId, id)
                .map(existingPractice -> {
                    existingPractice.setDone(updatedPractice.isDone());
                    existingPractice.setDate(updatedPractice.getDate());
                    return practiceRepository.save(existingPractice);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Practice not found"));
    }

    public void deletePractice(Long habitId, Long practiceId) {
        Practice practice = practiceRepository.findByHabitIdAndId(habitId, practiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Выполнение привычки с идентификатором " + practiceId + " не найдена для данной привычки"));
        practiceRepository.delete(practice);
    }
}
