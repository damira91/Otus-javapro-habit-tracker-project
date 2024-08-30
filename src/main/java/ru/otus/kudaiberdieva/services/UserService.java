package ru.otus.kudaiberdieva.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.kudaiberdieva.dtos.UserLoginRequestDto;
import ru.otus.kudaiberdieva.entities.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User createUser(User user);
   Optional<String> loginUser(UserLoginRequestDto userLoginDto);
    User updateUserProfile(User user);
    User save(User user);
    User findUserByEmail(String username);
    UserDetails loadUserByUsername(String username);
}
