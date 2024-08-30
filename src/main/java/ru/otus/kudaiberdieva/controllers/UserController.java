package ru.otus.kudaiberdieva.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kudaiberdieva.dtos.UserLoginRequestDto;
import ru.otus.kudaiberdieva.dtos.UserLoginResponseDto;
import ru.otus.kudaiberdieva.entities.Profile;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.security.SecurityUtils;
import ru.otus.kudaiberdieva.services.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth/users")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/register/")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping(path="/login/")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginRequestDto loginRequest) {
        Optional<String> jwtToken = userService.loginUser(loginRequest);
        if (jwtToken.isPresent()){
            return ResponseEntity.ok(new UserLoginResponseDto(jwtToken.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserLoginResponseDto("Authentication failed!"));
        }
    }

    @GetMapping(path="/profile/")
    public Profile getUserProfile(){
        return SecurityUtils.getCurrentLoggedInUser().getProfile();
    }

    @PutMapping(path="/profile/") //http://localhost:9009/auth/users/profile/
    public Profile updateUserProfile(@RequestBody Profile profile){
        User user = SecurityUtils.getCurrentLoggedInUser();
        user.setProfile(profile);
        return userService.updateUserProfile(user).getProfile();
    }
}
