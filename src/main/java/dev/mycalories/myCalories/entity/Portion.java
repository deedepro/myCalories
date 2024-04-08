package dev.mycalories.myCalories.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "portions")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @ManyToOne
    private Product product;
    @NonNull
    String name;
    @NonNull
    int count;
    @Nullable
    long portionId; //TODO - точно ли норм решение?

    //TODO создавать при создании продукта
    //TODO как сделать стандартные порции - гр и мл?
}
