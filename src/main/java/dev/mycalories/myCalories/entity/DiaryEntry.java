package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diary_entries")
public class DiaryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;

    @ManyToOne
    private Mealtime mealtime;

    @ManyToOne
    private Food food;

    private Double weight;

    public DiaryEntry(Date date, Mealtime mealtime, Food food, Double weight) {
        this.date = date;
        this.mealtime = mealtime;
        this.food = food;
        this.weight = weight;
    }
}
