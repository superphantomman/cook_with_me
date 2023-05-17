package com.superphantomman.cook_with_me.sections.ingredient.models.entities;

import com.superphantomman.cook_with_me.util.MeasurementType;
import com.superphantomman.cook_with_me.util.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "ingredient")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Ingredient {
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    //Per 100 ml or 100g
    @Column(name = "calories")
    private Integer calories;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    @ToString.Exclude
    @OneToMany(mappedBy = "id.ingredient")
    List<IngredientRecipe> ingredientRecipes = new ArrayList<>();

    @Transient
    public abstract State state();

    public List<IngredientRecipe> getIngredientRecipes() {
        return Collections.unmodifiableList(ingredientRecipes);
    }

    public Ingredient(String name, Integer calories, MeasurementType measurementType) {
        this.name = name;
        this.calories = calories;
        this.measurementType = measurementType;

    }

    public Ingredient(@NotNull Ingredient other) {
        this.id = other.id;
        this.name = other.name;
        this.calories = other.calories;
        this.measurementType = other.measurementType;
        this.ingredientRecipes = other.ingredientRecipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;
        return getId() != null && Objects.equals(getId(), that.getId()) && Objects.equals(state(), that.state());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
