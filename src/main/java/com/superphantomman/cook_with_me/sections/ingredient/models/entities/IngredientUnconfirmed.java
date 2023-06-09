package com.superphantomman.cook_with_me.sections.ingredient.models.entities;

import com.superphantomman.cook_with_me.util.MeasurementType;
import com.superphantomman.cook_with_me.util.State;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;



@NoArgsConstructor
@Table(name = "ingredient_unconfirmed")
@Entity(name = "ingredient_unconfirmed")
public class IngredientUnconfirmed extends Ingredient {


    public IngredientUnconfirmed(String name, Integer calories, MeasurementType measurementType) {
        super(name, calories, measurementType);
    }

    public IngredientUnconfirmed(Ingredient ingredient) {
        super(ingredient);
    }


    @Transient
    @Override
    public State state() {
        return State.UNCONFIRMED;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
