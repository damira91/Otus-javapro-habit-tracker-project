package ru.otus.kudaiberdieva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kudaiberdieva.entities.Practice;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PracticeRepository extends JpaRepository<Practice, Long> {
    List<Practice> findByDate(LocalDate date);
    List<Practice> findByUserId(Long userId);
}
