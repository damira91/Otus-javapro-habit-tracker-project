package ru.otus.kudaiberdieva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kudaiberdieva.entities.Habit;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findAllByUserId(Long userId);
    Optional<Habit> findByIdAndCategoryId(Long habitId,Long categoryId);
    Optional<Habit> findByIdAndUserId(Long id, Long userId);
}
