package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigInteger;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;
    @NonNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    @NonNull
    private BigInteger weight;
}
