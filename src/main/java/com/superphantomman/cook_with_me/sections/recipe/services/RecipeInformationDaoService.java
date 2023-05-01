package com.superphantomman.cook_with_me.sections.recipe.services;


import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.*;
import com.superphantomman.cook_with_me.sections.recipe.repositories.*;
import com.superphantomman.cook_with_me.util.AbstractDaoService;
import com.superphantomman.cook_with_me.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

import static com.superphantomman.cook_with_me.util.State.*;

@Service
public final class RecipeInformationDaoService extends AbstractDaoService<RecipeInformation> {


    private final EnumMap<State, JpaRepository<? extends RecipeInformation, Long>>
            information = new EnumMap<>(State.class);

    @Autowired
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeInformationDaoService(
            RecipeInformationRepository informationRepository,
            RecipeInformationPrivateRepository informationPrivate,
            RecipeInformationUnconfirmedRepository informationUnconfirmed,
            RecipeInformationConfirmedRepository informationConfirmed,
            RecipeRepository recipeRepository
    ) {

        super(informationRepository);
        this.information.put(PRIVATE, informationPrivate);
        this.information.put(CONFIRMED, informationConfirmed);
        this.information.put(UNCONFIRMED, informationUnconfirmed);

        this.recipeRepository = recipeRepository;

    }

    @Override
    /*
     * consider return type as id
     * */

    public boolean add(RecipeInformation e) {

        if (contains(e)) return false;

        return contains(
                _add(
                switch (e.state()) {
                    case PRIVATE -> new RecipeInformationPrivate(e);
                    case UNCONFIRMED -> new RecipeInformationUnconfirmed(e);
                    case CONFIRMED -> new RecipeInformationConfirmed(e);
                }));
    }

    @Override
    public List<? extends RecipeInformation> getAll(String search) {
        return getAll().stream()
                .filter(ri -> ri.getName().contains(search)).toList();
    }

    public List<? extends RecipeInformation> getAll(State state) {
        return information.get(state).findAll();
    }

    public boolean contains(RecipeInformation e, State state) {
        return state.equals(e.state()) && _contains(
                switch (e.state()) {
                    case PRIVATE -> new RecipeInformationPrivate(e);
                    case UNCONFIRMED -> new RecipeInformationUnconfirmed(e);
                    case CONFIRMED -> new RecipeInformationConfirmed(e);
                });
    }

    public boolean changeState(RecipeInformation e, State state) {
        return !state.equals(e.state()) && remove(e) &&
                add(
                        switch (state) {
                            case PRIVATE -> new RecipeInformationPrivate(e);
                            case UNCONFIRMED -> new RecipeInformationUnconfirmed(e);
                            case CONFIRMED -> new RecipeInformationConfirmed(e);
                        }
                );
    }

    public boolean addRecipe(Long recipeInfoId, Recipe r){
        if (recipeRepository.exists(Example.of(r)))
            return false;
        RecipeInformation ri = repository.findById(recipeInfoId).orElseThrow(
                () -> {throw new NotFoundEntityException(" There is no recipe information with given id = " + recipeInfoId ); }
        );
        ri.setRecipe(r);
        repository.save(ri);
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        for (State state : State.values()) {
            information.get(state).deleteAll();
        }
    }

    private <T extends RecipeInformation> T _add(T e) {
        return ((JpaRepository<T, Long>) information.get(e.state())).save(e);
    }

    private <T extends RecipeInformation> boolean _contains(T e) {
        return ((JpaRepository<T, Long>) information.get(e.state()))
                .exists(Example.of(e));
    }

}
