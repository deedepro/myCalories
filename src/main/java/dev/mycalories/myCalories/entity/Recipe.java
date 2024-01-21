package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe {
    @Id
    @SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", allocationSize = 1)
    @GeneratedValue(generator="my_seq")
    private Long id;
    @NonNull
    private String name;
    private BigInteger weight;
    @NonNull
    @ManyToOne
    private User user;
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private EnergyValue energyValue;
}
