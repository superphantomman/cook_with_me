package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
final public class IngredientService {

    private IngredientRepository repository;


    public List<Ingredient> ingredients(){

        return repository.findAll();
    }
}
