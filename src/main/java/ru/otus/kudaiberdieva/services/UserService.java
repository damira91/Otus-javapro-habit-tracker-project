package ru.otus.kudaiberdieva.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.dtos.UserLoginRequestDto;
import ru.otus.kudaiberdieva.dtos.UserLoginResponseDto;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.IncorrectCredentialsException;
import ru.otus.kudaiberdieva.exceptions.InformationExistException;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.UserRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> userOptional = findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new InformationExistException("Пользователь с почтой " + user.getEmail() + " уже существует.");
        }
        if (user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        return save(user);
    }
    public UserLoginResponseDto loginUser(UserLoginRequestDto loginRequest) {
        User user = findUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с такой почтой " + loginRequest.getEmail() + " не найден"));
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IncorrectCredentialsException("Некорректный пароль или почта");
        }
        return new UserLoginResponseDto("Login successful!");
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser (User user){
        User existingUser = findUserByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с электронной почтой: " + user.getEmail() + " не найден."));

        if (existingUser.equals(user)) {
            throw new InformationExistException("Информация в профиле та же. Обновления не требуется.");
        }
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setAge(user.getAge());

        return userRepository.save(existingUser);
    }
    public void delete(Long id) {
        User user = findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с идентификатором " + id + " не найден."));
        userRepository.delete(user);
    }
}
