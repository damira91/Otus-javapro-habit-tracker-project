package ru.otus.kudaiberdieva.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.kudaiberdieva.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmailAddress(String emailAddress);
}
