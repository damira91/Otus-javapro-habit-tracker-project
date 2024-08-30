package ru.otus.kudaiberdieva.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Category> categories;
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Habit> habits;
}
