package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.IngredientConfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.IngredientUnconfirmed;
import com.superphantomman.cook_with_me.util.DaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.superphantomman.cook_with_me.util.MeasurementType.*;


@Transactional
@SpringBootTest
public class IngredientServiceTest {
    @Autowired
    DaoService<Ingredient> ingredientDaoService;

    @Autowired
    public IngredientServiceTest(DaoService<Ingredient> ingredientDaoService) {
        this.ingredientDaoService = ingredientDaoService;
    }

    private List<Ingredient> ingredients = List.of(
            new IngredientConfirmed("watermelon", 10, GRAM), new IngredientConfirmed("orange", 20, GRAM),
            new IngredientUnconfirmed("beef", 20, DECAGRAM), new IngredientConfirmed("milk", 1, LITER)

    );
    @Test
    void contextLoads() {
        ingredientDaoService.addAll(ingredients);
    }
    @Test
    void contextClear(){ingredientDaoService.clear();
    }
    @Test
    void testRetrieve() {
        for (var e : ingredientDaoService.getAll()){
            System.out.println(e.getName());
        }


//        assert ingredients.stream().allMatch( i -> ingredientDaoService.contains(i));
//        assert !ingredientDaoService.contains(new IngredientConfirmed("wrong", -1, GRAM));
//        assert  ingredientDaoService.get(1L).getName().equals( ingredients.get(1).getName() );
//        assert ingredientDaoService.get(100L) == null;
    }



    @Test
    void testModifying(){

    }


}
