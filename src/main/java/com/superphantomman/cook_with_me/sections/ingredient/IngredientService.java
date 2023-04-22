package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import com.superphantomman.cook_with_me.util.AbstractDaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO test it
@Service
final public class IngredientService extends AbstractDaoService<Ingredient>  {
    public IngredientService(IngredientRepository repository) {
        super(repository);
    }

    @Override
    public List<? extends Ingredient> getAll(String search) {
        return getAll().stream().filter(( i ) -> i.getName().contains(search)).toList();
    }

}
