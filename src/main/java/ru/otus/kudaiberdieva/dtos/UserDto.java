package ru.otus.kudaiberdieva.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kudaiberdieva.entities.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;

    public static UserDto entityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge());
    }

    public User dtoToEntity(Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail(this.email);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setAge(this.age);
        return user;
    }
}

