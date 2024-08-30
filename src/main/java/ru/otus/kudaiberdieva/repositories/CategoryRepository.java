package ru.otus.kudaiberdieva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kudaiberdieva.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameAndUserId(String categoryName, Long userId);
    Category findByIdAndUserId(Long categoryId, Long userId);
}
