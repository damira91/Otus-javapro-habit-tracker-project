package ru.otus.kudaiberdieva.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "habit_tracks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean done;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
