package ru.otus.kudaiberdieva.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Habit> habitList;
}
