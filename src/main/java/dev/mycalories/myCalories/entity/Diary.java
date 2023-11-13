package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "diary_entries")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Date date;
    @NonNull
    @ManyToOne
    private Mealtime mealtime;
    @NonNull
    @ManyToOne
    private Food food;
    @NonNull
    private Double weight;
}
