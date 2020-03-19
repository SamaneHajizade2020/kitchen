package kitchen;

import dao.Ingredient;
import dao.Recipe;

import java.util.Collection;
import java.util.ListIterator;

public interface RecipeService {

    public abstract void createRecipe(Recipe Recipe);
    public abstract void updateRecipe(String id, Recipe Recipe);
    public abstract void deleteRecipe(String id);
    public abstract void save(String id, Recipe recipe);
    public abstract Collection<Recipe> getRecipes();

    public abstract Collection<Ingredient> getIngredients();
    public abstract void removeIngredient(String id, Ingredient ingredient);
    public abstract void createIngredient(Ingredient ingredient);
}
