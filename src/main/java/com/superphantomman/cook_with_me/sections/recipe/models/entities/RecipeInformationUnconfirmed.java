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
import javax.persistence.Transient;
import java.time.LocalDate;


/**
 * Recipe which was marked by user to be published
 * and is waiting for confirmation by moderator
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "recipe_unconfirmed_information")
@Entity(name = "RecipeInformationUnconfirmed")
@DiscriminatorValue("UNCONFIRMED")
public class RecipeInformationUnconfirmed extends RecipeInformation {

    @Transient
    @Override
    public State state() {
        return State.UNCONFIRMED;
    }

    public RecipeInformationUnconfirmed(String name, LocalDate creationDate) {
        super(name, creationDate);
    }

    public RecipeInformationUnconfirmed(String name) {
        super(name);
    }

    public RecipeInformationUnconfirmed(RecipeInformation ri) {
        super(ri);
    }


}
