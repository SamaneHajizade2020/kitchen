package kitchen;

import dao.Recipe;
import dao.Result;

import java.util.Collection;

public interface RecipeService {

    public abstract void createRecipe(Recipe Recipe);
    public abstract void updateRecipe(String id, Recipe Recipe);
    public abstract void deleteRecipe(String id);
    public abstract void save(String id, Recipe recipe);
    public abstract Collection<Recipe> getRecipes();
    public abstract Collection<Result> getResult();

}
