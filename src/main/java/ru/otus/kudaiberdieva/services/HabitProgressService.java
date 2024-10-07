package ru.otus.kudaiberdieva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.HabitProgress;
import ru.otus.kudaiberdieva.entities.Practice;
import ru.otus.kudaiberdieva.repositories.HabitProgressRepository;
import ru.otus.kudaiberdieva.repositories.PracticeRepository;

import java.util.List;

@Service
public class HabitProgressService {
    private final PracticeRepository practiceRepository;
    private final HabitProgressRepository habitProgressRepository;

    @Autowired
    public HabitProgressService(PracticeRepository practiceRepository, HabitProgressRepository habitProgressRepository) {
        this.practiceRepository = practiceRepository;
        this.habitProgressRepository = habitProgressRepository;
    }

    public HabitProgress calculateProgressForHabit(Long habitId) {
        List<Practice> completedPractices = practiceRepository.findByHabitIdAndDoneTrue(habitId);
        int progress = completedPractices.size();

        String habitName = completedPractices.isEmpty() ? "Unknown Habit" : completedPractices.get(0).getHabit().getName();

        HabitProgress habitProgress = new HabitProgress();
        habitProgress.setHabitName(habitName);
        habitProgress.setProgress(progress);

        return habitProgressRepository.save(habitProgress);
    }
}
