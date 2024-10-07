package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterDto {
    private String email;
    private String password;
    private String confirmedPassword;
    private String firstName;
    private String lastName;
    private Integer age;

    public User dtoToEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setAge(this.age);
        return user;
    }
}

