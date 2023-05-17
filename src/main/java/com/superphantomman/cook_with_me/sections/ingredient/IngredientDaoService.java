package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.exceptions.NotPersistedEntityException;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientConfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientUnconfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.repositories.IngredientConfirmedRepository;
import com.superphantomman.cook_with_me.sections.ingredient.repositories.IngredientRepository;
import com.superphantomman.cook_with_me.sections.ingredient.repositories.IngredientUnconfirmedRepository;
import com.superphantomman.cook_with_me.util.AbstractDaoService;
import com.superphantomman.cook_with_me.util.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

import static com.superphantomman.cook_with_me.util.State.CONFIRMED;
import static com.superphantomman.cook_with_me.util.State.UNCONFIRMED;


//TODO add getAll(State) and optimization with queries
@Service
public class IngredientDaoService extends AbstractDaoService<Ingredient> {

    private final EnumMap<State, JpaRepository<? extends Ingredient, Long>>
            ingredients = new EnumMap<>(State.class);

    public IngredientDaoService(
            IngredientRepository repository, IngredientUnconfirmedRepository ingredientUnconfirmedRepository,
            IngredientConfirmedRepository ingredientConfirmedRepository
    ) {
        super(repository);
        ingredients.put(UNCONFIRMED, ingredientUnconfirmedRepository);
        ingredients.put(CONFIRMED, ingredientConfirmedRepository);


    }

    @Override
    public boolean add(Ingredient e) {
        for (final Ingredient i : getAll(e.getName())) {
            if (e.getName().equals(i.getName())) {
                throw new NotPersistedEntityException("Ingredient with this name is already present in database");
            }
        }

        Ingredient savedIngredient = _add(
                switch (e.state()) {
                    case UNCONFIRMED, PRIVATE -> new IngredientUnconfirmed(e);
                    case CONFIRMED -> new IngredientConfirmed(e);
                }
        );

        e.setId(savedIngredient.getId());

        return contains(savedIngredient);
    }

    private <T extends Ingredient> T _add(T e) {
        return ((JpaRepository<T, Long>) ingredients.get(e.state())).save(e);
    }


    @Override
    public List<Ingredient> getAll(String search) {
        return getAll().stream().filter((i) -> search.contains(i.getName())).toList();
    }

    public Ingredient get(String search) {


        return getAll().stream().filter(i -> search.equals(i.getName()))
                .findFirst().orElseThrow(() -> new NotFoundEntityException("Entity with given name not present in database"));
    }

}
