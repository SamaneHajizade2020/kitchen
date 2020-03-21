package kitchen;

import dao.Ingredient;

import javax.xml.bind.Element;
import java.util.Collection;

public interface IngredientService {

    public Collection<Element> getElements();
    public abstract Ingredient createIngredient(Ingredient ingredient);
    public abstract Ingredient saveIngredient(Ingredient ingredient);
    public abstract void deleteIngredient(String id);
    public void updateIngredient(String id, Ingredient ingredient);
    public abstract Collection<Ingredient> getIngredients();

    public abstract void removeIngredient(String id, Ingredient ingredient);
    public abstract void removeIngredient(Ingredient ingredient);
}
