package com.superphantomman.cook_with_me.sections.recipe.models.forms;

import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationPrivate;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationUnconfirmed;
import com.superphantomman.cook_with_me.util.Form;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;



@Data
public class RecipeInformationForm implements Form<RecipeInformation> {

    @Pattern(regexp = "[a-zA-Z ]+", message = "Invalid name for Recipe ")
    @NotBlank(message = "Name is required")
    private String name;

    private Boolean isPrivate = false;

    public RecipeInformation toEntity() {
        if (isPrivate)
            return new RecipeInformationPrivate(name);
        return new RecipeInformationUnconfirmed(name);
    }

}
