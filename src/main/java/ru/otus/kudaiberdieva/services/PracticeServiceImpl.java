package ru.otus.kudaiberdieva.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.entities.Habit;
import ru.otus.kudaiberdieva.entities.Practice;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.PracticeRepository;
import ru.otus.kudaiberdieva.security.MyUserDetails;
import ru.otus.kudaiberdieva.security.SecurityUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PracticeServiceImpl implements PracticeService {
    private final PracticeRepository practiceRepository;
    private final CategoryService categoryService;
    Logger logger = Logger.getLogger(Practice.class.getName());


    public Practice createPractice(Long habitId, Practice practice){
        Optional<Habit> habitOptional = (categoryService.getHabitById(habitId));
        if (habitOptional.isPresent()){
            if (practice.getDate().isAfter(LocalDate.now())){
                throw new IllegalArgumentException("Date must equal or before today's date");
            }
            practice.setUser(SecurityUtils.getCurrentLoggedInUser());
            practice.setHabit(habitOptional.get());
            return practiceRepository.save(practice);
        } else {
            throw new ResourceNotFoundException("Habit with id " + habitId + " not found.");
        }
    }

    public Practice getPracticeById(Long practiceId){
        Optional<Practice> practiceOptional = practiceRepository.findById(practiceId);

        if (practiceOptional.isPresent()){
            return practiceOptional.get();
        } else {
            throw new ResourceNotFoundException("Practice with id " + practiceId + "not found.");
        }
    }

    public List<Practice> getPracticeByDate(LocalDate date){
        List<Practice> practiceList = practiceRepository.findByDate(date);
        if (practiceList.isEmpty()){
            throw new ResourceNotFoundException("No practices found on " + date);
        }
        return practiceList;
    }

    public List<Practice> getAllPractices(){
        List<Practice> practiceList = practiceRepository.findByUserId(SecurityUtils.getCurrentLoggedInUser().getId());
        if (practiceList.isEmpty()){
            throw new ResourceNotFoundException("No practices found.");
        }
        return practiceList;
    }

    public Practice updatePractice(Practice practice) throws IllegalAccessException {
        Optional<Practice> practiceOptional = practiceRepository.findById(practice.getId());
        if (practice.getDate().isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date must equal or before today's date");
        }
        if (practiceOptional.isPresent()){
            try {
                for (Field field : Practice.class.getDeclaredFields()) { //loop through class fields
                    field.setAccessible(true); //make private fields accessible
                    Object newValue = field.get(practice);
                    Object originalValue = field.get(practiceOptional.get());
                    if (newValue != null && !newValue.equals(originalValue)) { //if not null and different from original
                        field.set(practiceOptional.get(), newValue);
                    }
                }
                return practiceRepository.save(practiceOptional.get());
            } catch (IllegalArgumentException e){
                throw new IllegalAccessException(e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException("Practice with id " + practice.getId() + "not found.");
        }
    }

    public void deletePractice(Long practiceId) {
        if (practiceId != null) {
            practiceRepository.deleteById(practiceId);
        } else {
            throw new ResourceNotFoundException("practice with id " + practiceId + " not found");
        }
    }
}
