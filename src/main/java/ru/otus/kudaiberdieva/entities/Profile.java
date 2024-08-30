package ru.otus.kudaiberdieva.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String bio;
    @OneToOne(mappedBy = "profile")
    private User user;

}
