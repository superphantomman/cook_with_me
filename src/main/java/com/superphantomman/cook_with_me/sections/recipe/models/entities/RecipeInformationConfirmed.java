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
 * Recipe confirmed by moderators for displaying to other users
 * */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "recipe_confirmed_information")
@Entity(name = "RecipeInformationConfirmed")
@DiscriminatorValue("CONFIRMED")
public class RecipeInformationConfirmed extends RecipeInformation {


    public RecipeInformationConfirmed(String name, LocalDate creationDate) {
        super(name, creationDate);
    }

    public RecipeInformationConfirmed(String name) {
        super(name);
    }

    public RecipeInformationConfirmed(RecipeInformation ri){
        super(ri);
    }
    @Transient
    @Override
    public State state() {
        return State.CONFIRMED;
    }
}
