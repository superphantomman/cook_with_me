package com.superphantomman.cook_with_me.sections.recipe.models.entities;


import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.util.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.beans.Transient;
import java.time.LocalDate;


/**
 * Recipe which can be seen only by author.
 */

@Getter
@Setter
@ToString
@Table(name = "recipe_private")
@NoArgsConstructor
@Entity(name = "RecipeInformationPrivate")
@DiscriminatorValue("PRIVATE")
public class RecipeInformationPrivate extends RecipeInformation {


    public RecipeInformationPrivate(String name, LocalDate creationDate) {
        super(name, creationDate);
    }

    public RecipeInformationPrivate(String name) {
        super(name);
    }

    public RecipeInformationPrivate(RecipeInformation ri) {
        super(ri);
    }


    @Transient
    @Override
    public State state() {
        return State.PRIVATE;
    }
}
