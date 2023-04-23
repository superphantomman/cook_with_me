package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.exceptions.NotPersistedEntityException;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import com.superphantomman.cook_with_me.util.AbstractDaoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
final public class IngredientService extends AbstractDaoService<Ingredient>  {
    public IngredientService(IngredientRepository repository) {
        super(repository);
    }

    @Override
    public boolean add(Ingredient e) {

        for (final Ingredient i : getAll(e.getName())){
            if (e.getName().equals(i.getName())) {
                throw new NotPersistedEntityException("Ingredient with this name is already present in database");
            }        }
        return super.add(e);
    }

    @Override
    public List<? extends Ingredient> getAll(String search) {
        return getAll().stream().filter(( i ) -> i.getName().contains(search)).toList();
    }

}
