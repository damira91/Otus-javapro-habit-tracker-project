package ru.otus.kudaiberdieva.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String goal;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",  nullable = false)
    private Category category;
    @OneToMany(mappedBy = "habit", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Practice> practices;
}
