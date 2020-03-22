package kitchen;

import dao.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import java.util.logging.Logger;

@Service
public class RecipeServiceImp implements RecipeService {
    private final static Logger LOGGER = Logger.getLogger(RecipeServiceImp.class.getName());

    public static Map<String, Recipe> productRepo = new HashMap<>();
    public static Map<String, Ingredient> ingredientRepo = new HashMap<>();
    public static Map<String, Result> resultRepo = new HashMap<>();


//
//
//    static {
//
//        // ArrayList<Ingredient> ingredientArrayList = new ArrayList();
//        Recipe honeyCake = new Recipe();
//        honeyCake.setId("1");
//        honeyCake.setName("HoneyCake");
//        honeyCake.setInstructions("HoneyCake is mixed of honey and milk and suger!");
//
//
//        Ingredient ingredient1 = new Ingredient("1", "suger", 1);
//        Ingredient ingredient2 = new Ingredient("2", "flor", 10);
//        Ingredient ingredient11 = new Ingredient("10", " Brown suger", 1);
//        Ingredient ingredient22 = new Ingredient("110", "vanilla", 2);
//
////       Ingredient ingredient1 = new Ingredient( "suger", 1);
////        Ingredient ingredient2 = new Ingredient( "flor", 10);
////        Ingredient ingredient11 = new Ingredient( " Brown suger", 1);
////        Ingredient ingredient22 = new Ingredient( "vanilla", 2);*/
////
////        MyElement MyElement = new MyElement( "suger", 1);
////        MyElement MyElement2 = new MyElement( "flor", 10);
////        MyElement MyElement3 = new MyElement( " Brown suger", 1);
////        MyElement MyElement4 = new MyElement( "vanilla", 2);
////        MyElement MyElementList [] = {MyElement, MyElement2, MyElement3 ,MyElement4};*/
//
//        honeyCake.setIngredients(Arrays.asList(ingredient1,ingredient2));
//        productRepo.put(honeyCake.getId(), honeyCake);
//
//        Recipe almondCake = new Recipe();
//        almondCake.setId("2");
//        almondCake.setName("AlmondCake");
//        almondCake.setInstructions("almondCake is mixed of almond and milk and suger!");
//        almondCake.setIngredients(Arrays.asList(ingredient11,ingredient22));
//        //almondCake.setElements(MyElementList);
//
//        productRepo.put(almondCake.getId(), almondCake);
//
//        ingredientRepo.put(ingredient1.getId(), ingredient1);
//        ingredientRepo.put(ingredient2.getId(), ingredient2);
//        ingredientRepo.put(ingredient11.getId(), ingredient11);
//        ingredientRepo.put(ingredient22.getId(), ingredient22);
//    }
    
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


    public  Collection<Result> getResult(){
        return resultRepo.values();
    }

    public void getCountByRecipe(Collection<Recipe> recipes, Collection<Ingredient> ingredients) {
        logForRecipe(recipes);
        logForIngredient(ingredients);
        for (Recipe recipe : recipes) {
            ArrayList<Ingredient> resultArr= new ArrayList<>();
            List<Ingredient> ingredientsOfRecipe = recipe.getIngredients();

            logForRecipe(recipe);

            List<Ingredient> ingredientOfIngredientsWhichAreInThisRecipe = ingredients.stream()
                    .filter(os -> ingredientsOfRecipe.stream()                    // filter
                            .anyMatch(ns ->                                  // compare both
                                    os.getName().equals(ns.getName())))
                    .collect(Collectors.toList());

            LOGGER.info("Ingredient that are in this recipe:" + ingredientOfIngredientsWhichAreInThisRecipe.size());
            logForIngredient(ingredientOfIngredientsWhichAreInThisRecipe);

            resultArr.addAll(ingredientsOfRecipe);
            resultArr.addAll(ingredientOfIngredientsWhichAreInThisRecipe);
            LOGGER.info("resultArr:" + resultArr.size());
            logForIngredient(resultArr);

            List<Ingredient> list = divOfQuantityForSameIngredient(resultArr);
            logForIngredient(list);

            Integer quantity =  finMinInList(list);
            LOGGER.info("min by comprator" + quantity);

            resultRepo.put(String.valueOf(new Random().nextInt()),
                    new Result(String.valueOf(new Random().nextInt()), String.valueOf(quantity)));
        }

    }

    private Integer finMinInList(List<Ingredient> list) {
        return list
                .stream()
                .min(Comparator.comparing(Ingredient::getQuantity))
                .get().getQuantity();
    }

    private void logForIngredient(Collection<Ingredient> ingredients) {
        System.out.printf("Size of ing" + ingredients.size());
        for (Ingredient ingredient : ingredients) {
            LOGGER.info( " " + ingredient.getName() + " " + ingredient.getQuantity());
        }
    }

    private void logForRecipe(Recipe recipe) {
        LOGGER.info("Ingredient of recipe" + recipe.getId());
        for (Ingredient ingredient : recipe.getIngredients()) {
            LOGGER.info( " " + ingredient.getName() + " " + ingredient.getQuantity());
        }
    }

    private void logForRecipe(Collection<Recipe> recipes) {
        System.out.printf("size of recipe" + recipes.size() + " " );
        for (Recipe recipe : recipes) {
            LOGGER.info( recipe.getId() + " " + recipe.getName() + recipe.getInstructions() + " " + recipe.getIngredients().size());
            List<Ingredient> ingredients1 = recipe.getIngredients();
            for (Ingredient ingredient : ingredients1) {
                LOGGER.info( " " + ingredient.getName() + " " + ingredient.getQuantity());
            }
        }
    }

    public List<Ingredient> divOfQuantityForSameIngredient(List<Ingredient> listIngredient) {
        ArrayList<Ingredient> result= new ArrayList<>();
        for (int i = 0; i < listIngredient.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((listIngredient.get(i).getName().equalsIgnoreCase(listIngredient.get(j).getName()) && ( i !=j)
                        && (listIngredient.get(i).getQuantity()!= 0) && (listIngredient.get(j).getQuantity()!= 0)))  {
                    LOGGER.info("name:" + listIngredient.get(i).getName() + " " + "Quantity:" + listIngredient.get(i).getQuantity());
                    LOGGER.info("name:" + listIngredient.get(j).getName() + " " + "Quantity:" + listIngredient.get(j).getQuantity());
                    int resut = ((listIngredient.get(i).getQuantity()) / (listIngredient.get(j).getQuantity()));
                    result.add(new Ingredient(String.valueOf(new Random().nextInt()),listIngredient.get(i).getName(), resut));
                }
            }
        }

        return result;
    }

}
