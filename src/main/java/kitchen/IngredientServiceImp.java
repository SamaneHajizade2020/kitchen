package kitchen;

import dao.Ingredient;
import dao.MyElement;
import org.springframework.stereotype.Service;

import javax.xml.bind.Element;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Service
public class IngredientServiceImp implements IngredientService {


    static {
        MyElement MyElement = new MyElement( "suger", 1);
        MyElement MyElement2 = new MyElement( "flor", 10);
        MyElement MyElement3 = new MyElement( " Brown suger", 1);
        MyElement MyElement4 = new MyElement( "vanilla", 2);

        MyElement myElementList [] = {MyElement, MyElement2, MyElement3 ,MyElement4};
    }

    public static Map<String, Ingredient> ingredientRepo = new HashMap<>();


    static {

        Ingredient ingredient1 = new Ingredient("1", "suger", 1);
        Ingredient ingredient2 = new Ingredient("2", "flor", 10);
        Ingredient ingredient11 = new Ingredient("10", " Brown suger", 1);
        Ingredient ingredient22 = new Ingredient("110", "vanilla", 2);


        ingredientRepo.put(ingredient1.getId(), ingredient1);
        ingredientRepo.put(ingredient2.getId(), ingredient2);
        ingredientRepo.put(ingredient11.getId(), ingredient11);
        ingredientRepo.put(ingredient22.getId(), ingredient22);
    }


    private MyElement[] myElementList;


    public Collection<Element> getElements(){
        return null;
    }



    public Ingredient createIngredient(Ingredient ingredient){
        return   ingredientRepo.put(ingredient.getId(), ingredient);
    }

    public Ingredient saveIngredient(Ingredient ingredient){
        return   ingredientRepo.put(ingredient.getName(), ingredient);
    }


    @Override
    public void updateIngredient(String id, Ingredient ingredient){
        ingredientRepo.remove(id);
        ingredient.setId(id);
        ingredientRepo.put(id, ingredient);
    }

    public void saveIngredient(Integer id, Ingredient ingredient){
        ingredientRepo.put(ingredient.getId(), ingredient);
    }

    @Override
    public void deleteIngredient(String id){
        ingredientRepo.remove(id);
    }

    public Collection<Ingredient> getIngredients(){
        return ingredientRepo.values();
    }

    @Override
    public  void removeIngredient( Ingredient ingredient){
        ingredientRepo.remove( ingredient);
    }


    @Override
    public  void removeIngredient(String id, Ingredient ingredient){
        ingredientRepo.remove(id, ingredient);
    }


}
