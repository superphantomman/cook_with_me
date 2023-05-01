package com.superphantomman.cook_with_me.sections.recipe.models.entities;


import com.superphantomman.cook_with_me.util.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "recipe_information")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

abstract public class RecipeInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "int")
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;


    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Recipe recipe;

    @Column(name = "creation_date")
    private LocalDate creationDate;


    public RecipeInformation(String name,  LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public RecipeInformation(String name) {
        this.name = name;
        this.creationDate = LocalDate.now();
    }

    public RecipeInformation(RecipeInformation other){
        this.id = other.id;
        this.name = other.name;
        this.creationDate = other.creationDate;
        this.recipe = other.recipe;
    }

    @Transient
    public abstract State state();
}
