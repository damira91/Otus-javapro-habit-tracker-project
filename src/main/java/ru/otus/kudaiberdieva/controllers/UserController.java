package ru.otus.kudaiberdieva.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.kudaiberdieva.dtos.*;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmedPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        User newUser = userRegisterDto.dtoToEntity();
        User createdUser = userService.createUser(newUser);
        return ResponseEntity.ok(UserDto.entityToDto(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginRequestDto loginRequest) {
        UserLoginResponseDto responseDto = userService.loginUser(loginRequest);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable Long id) {
        User existingUser = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        return UserDto.entityToDto(existingUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User userToUpdate = userDto.dtoToEntity(id);
        User updatedUser = userService.updateUser(userToUpdate);
        UserDto updatedUserDto = UserDto.entityToDto(updatedUser);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
