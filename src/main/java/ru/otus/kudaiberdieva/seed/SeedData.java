package ru.otus.kudaiberdieva.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.kudaiberdieva.entities.Category;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.Profile;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.repositories.CategoryRepository;
import ru.otus.kudaiberdieva.repositories.HabitRepository;
import ru.otus.kudaiberdieva.repositories.UserRepository;

@Component
public class SeedData implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final HabitRepository habitRepository;

    @Autowired
    public SeedData(@Lazy PasswordEncoder passwordEncoder, //loads on-demand
                    UserRepository userRepository,
                    CategoryRepository categoryRepository,
                    HabitRepository habitRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.habitRepository = habitRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("mail@mail.com");
        user.setPassword(passwordEncoder.encode("pass1234"));
        user.setProfile(new Profile());

        Category category = new Category();
        category.setName("Morning Routine");
        category.setDescription("Habits related to morning routine");
        category.setUser(user);
        userRepository.save(user);
        categoryRepository.save(category);

        Habit habit = new Habit();
        habit.setName("Shower");
        habit.setOutcome("Starting the day fresh");
        habit.setCategory(category);
        habit.setUser(user);
        habitRepository.save(habit);

        User user2 = new User();
        user2.setEmail("mail@mail.com");
        user2.setPassword(passwordEncoder.encode("pass1234"));
        user2.setProfile(new Profile());
        userRepository.save(user2);

        Category category1 = new Category();
        category1.setName("Bed Time");
        category1.setDescription("Habits related to bed routine");
        category1.setUser(user2);
        categoryRepository.save(category1);

        Habit habit1 = new Habit();
        habit1.setName("Skin Care");
        habit1.setOutcome("Improved self esteem");
        habit1.setCategory(category1);
        habit1.setUser(user2);
        habitRepository.save(habit1);
    }
}
