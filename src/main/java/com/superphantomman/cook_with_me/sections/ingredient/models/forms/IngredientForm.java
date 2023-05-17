package com.superphantomman.cook_with_me.sections.ingredient.models.forms;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientUnconfirmed;
import com.superphantomman.cook_with_me.util.Form;
import com.superphantomman.cook_with_me.util.MeasurementType;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public final class IngredientForm implements Form<Ingredient> {

    @Pattern(regexp = "[a-zA-Z ]+", message = "Invalid name for ingredient ")
    @NotBlank(message = "Name is required")
    private  String name;

    @Min(value = 0, message = "Calories must be higher than or equal 0")
    @NotNull(message = "Calories are required")
    private  Integer calories;

    @NotNull(message = "Measurement unit is required")
    private  MeasurementType mt;
    public Ingredient toEntity(){
        return new IngredientUnconfirmed(name, calories, mt);
    }

}
