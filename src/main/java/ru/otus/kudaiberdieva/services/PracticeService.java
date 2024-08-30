package ru.otus.kudaiberdieva.services;

import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.Practice;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PracticeService {
    Practice createPractice(Long habitId, Practice practice);
    Practice getPracticeById(Long habitId);
    List<Practice> getPracticeByDate(LocalDate date);
    List<Practice> getAllPractices();
    Practice updatePractice(Practice practice) throws IllegalAccessException;
    void deletePractice(Long practiceId);
}
