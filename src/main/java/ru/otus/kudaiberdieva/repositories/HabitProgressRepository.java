package ru.otus.kudaiberdieva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kudaiberdieva.entities.HabitProgress;

@Repository
public interface HabitProgressRepository  extends JpaRepository<HabitProgress, Long> {
}
