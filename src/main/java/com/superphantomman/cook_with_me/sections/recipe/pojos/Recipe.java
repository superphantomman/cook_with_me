package com.superphantomman.cook_with_me.sections.recipe.pojos;


import com.superphantomman.cook_with_me.sections.ingredient.pojos.*;
import com.superphantomman.cook_with_me.util.MeasurementType;
import com.superphantomman.cook_with_me.util.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;



@Getter
@Setter
@ToString
@NoArgsConstructor

@Table(name = "recipe")
@Entity
public class Recipe {


    @Id
    private Long id;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    //Being in possession of recipeInformation
    @OneToOne
    @JoinColumn(name = "id", columnDefinition = "int")
    @MapsId
    private RecipeInformation recipeInformation;


    @ToString.Exclude
    @OneToMany(mappedBy = "id.recipe", cascade = ALL, orphanRemoval = true)
    List<IngredientRecipe> ingredientRecipes = new ArrayList<>(7);

    /*
    Allow user to get view on collection, but not to modify
    User should explicit copy values from view
    * */
    public List<IngredientRecipe> getIngredientRecipes() {
        return Collections.unmodifiableList(ingredientRecipes);
    }

    public boolean addIngredient(Ingredient i, Float weight, MeasurementType mt) {
        if (i == null || weight == null || mt == null)
            throw new NullPointerException();
        if (weight.doubleValue() <= 0d)
            throw new IllegalArgumentException("Illegal argument for weigh parameter");

        if (i.state() == State.UNCONFIRMED && recipeInformation.state() == State.CONFIRMED)
            return false;

        return addIngredient(new IngredientRecipe(this, i, weight, mt));
    }

    /*
     * Remove first occurrence of element
     * */
    public boolean removeIngredient(Ingredient i) {
        for (var ir : ingredientRecipes) {
            if (ir.getIngredient().equals(i))
                return ingredientRecipes.remove(ir);
        }
        return false;
    }

    public boolean addIngredient(IngredientRecipe ir) {
        return ingredientRecipes.add(ir);

    }

    public Recipe(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return getId() != null && Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
