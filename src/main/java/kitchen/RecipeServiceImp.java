package kitchen;

import dao.Ingredient;
import dao.Recipe;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImp implements RecipeService {

    public static Map<String, Recipe> productRepo = new HashMap<>();
    public static Map<String, Ingredient> ingredientRepo = new HashMap<>();


    static {

        // ArrayList<Ingredient> ingredientArrayList = new ArrayList();
        Recipe honeyCake = new Recipe();
        honeyCake.setId("1");
        honeyCake.setName("HoneyCake");
        honeyCake.setInstructions("HoneyCake is mixed of honey and milk and suger!");

        Ingredient ingredient1 = new Ingredient("1", "suger", 0);
        Ingredient ingredient2 = new Ingredient("11", "flor", 10);
        Ingredient ingredient11 = new Ingredient("10", " Brown suger", 1);
        Ingredient ingredient22 = new Ingredient("110", "vanilla", 2);

        honeyCake.setIngredients(Arrays.asList(ingredient1,ingredient2));
        productRepo.put(honeyCake.getId(), honeyCake);

        Recipe almondCake = new Recipe();
        almondCake.setId("2");
        almondCake.setName("AlmondCake");
        almondCake.setInstructions("almondCake is mixed of almond and milk and suger!");
        almondCake.setIngredients(Arrays.asList(ingredient11,ingredient22));

        productRepo.put(almondCake.getId().toString(), almondCake);

        ingredientRepo.put(ingredient11.getId(), ingredient11);
        ingredientRepo.put(ingredient1.getId(), ingredient1);
        ingredientRepo.put(ingredient2.getId(), ingredient2);
        ingredientRepo.put(ingredient22.getId(), ingredient22);
    }
    
    @Override
    public void createRecipe(Recipe Recipe) {
        productRepo.put(Recipe.getId(), Recipe);
    }

    @Override
    public void updateRecipe(String id, Recipe recipe) {
        if(id != null){
            productRepo.remove(id);
            recipe.setId(id);
            productRepo.put(String.valueOf(id), recipe);
        }
    }

    @Override
    public void deleteRecipe(String id) {
        productRepo.remove(id);

    }

    @Override
    public void save(String id, Recipe recipe) {
        productRepo.remove(id, recipe);
        recipe.setId(id);
        productRepo.put(String.valueOf(id), recipe);

    }

    @Override
    public Collection<Recipe> getRecipes() {
        return productRepo.values();
    }

    @Override
    public  void removeIngredient(String id, Ingredient ingredient){
        ingredientRepo.remove(id, ingredient);
    }

    public Collection<Ingredient> getIngredients(){
        return ingredientRepo.values();
    }


    public void createIngredient(Ingredient ingredient){
        ingredientRepo.put(ingredient.getId(), ingredient);
    }
}
