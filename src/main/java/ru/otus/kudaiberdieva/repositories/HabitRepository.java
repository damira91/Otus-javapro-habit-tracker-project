package ru.otus.kudaiberdieva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kudaiberdieva.entities.Habit;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    Habit findByName(String habitName);

    List<Habit> findByUserId(Long userId);
}
