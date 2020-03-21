package kitchen;

import dao.MyElement;
import dao.Ingredient;
import dao.MyElement;
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


        Ingredient ingredient1 = new Ingredient("1", "suger", 1);
        Ingredient ingredient2 = new Ingredient("2", "flor", 10);
        Ingredient ingredient11 = new Ingredient("10", " Brown suger", 1);
        Ingredient ingredient22 = new Ingredient("110", "vanilla", 2);

/*        Ingredient ingredient1 = new Ingredient( "suger", 1);
        Ingredient ingredient2 = new Ingredient( "flor", 10);
        Ingredient ingredient11 = new Ingredient( " Brown suger", 1);
        Ingredient ingredient22 = new Ingredient( "vanilla", 2);*/

        MyElement MyElement = new MyElement( "suger", 1);
        MyElement MyElement2 = new MyElement( "flor", 10);
        MyElement MyElement3 = new MyElement( " Brown suger", 1);
        MyElement MyElement4 = new MyElement( "vanilla", 2);
        MyElement MyElementList [] = {MyElement, MyElement2, MyElement3 ,MyElement4};

        honeyCake.setIngredients(Arrays.asList(ingredient1,ingredient2));
        productRepo.put(honeyCake.getId(), honeyCake);

        Recipe almondCake = new Recipe();
        almondCake.setId("2");
        almondCake.setName("AlmondCake");
        almondCake.setInstructions("almondCake is mixed of almond and milk and suger!");
        almondCake.setIngredients(Arrays.asList(ingredient11,ingredient22));
        almondCake.setElements(MyElementList);

        productRepo.put(almondCake.getId().toString(), almondCake);

        ingredientRepo.put(ingredient1.getId(), ingredient1);
        ingredientRepo.put(ingredient2.getId(), ingredient2);
        ingredientRepo.put(ingredient11.getId(), ingredient11);
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
            productRepo.put(id, recipe);
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

    @Override
    public  void removeIngredient( Ingredient ingredient){
        ingredientRepo.remove( ingredient);
    }

    public Collection<Ingredient> getIngredients(){
        return ingredientRepo.values();
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

/*    public ResponseEntity<Object> createIngredient(@RequestBody ArrayList<Ingredient> ingredients) {

//        if(ingredient.getQuantity() <= 0)
//            return  new ResponseEntity<>("Rejected", HttpStatus.NOT_ACCEPTABLE);

        // controlIngredientQuantity(ingredient);

        for (Ingredient ingredient : ingredients) {
            createIngredient(ingredient);
        }
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }
*/
/*
    public void controlIngredientQuantity(Ingredient ingredient) {

        ArrayList<Ingredient> items = listOfIngredients();

        for (Ingredient ingredient1 : getIngredients()) {
            if (items.contains(ingredient1.getName()))
            {
                Integer sum = items.stream()
                        .map(x -> x.getQuantity())
                        .reduce(0, Integer::sum);

                ingredient1.setQuantity(sum);
            }
            updateIngredient(ingredient1.getId(), ingredient1);
        }

    }

    private ArrayList<Ingredient> listOfIngredients() {
        ArrayList<Ingredient> items = new ArrayList<>();

        for (Ingredient x : getIngredients()) {
            items.add(x);
        }
        return items;
    }
*/



}
