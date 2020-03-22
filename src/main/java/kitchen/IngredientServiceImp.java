package kitchen;

import dao.Ingredient;
import dao.MyElement;
import dao.Result;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.Element;
import java.util.*;
import java.util.stream.Collectors;

import static kitchen.RecipeServiceImp.ingredientRepo;


@Service
public class IngredientServiceImp implements IngredientService {

    static final Logger logger = Logger.getLogger(IngredientServiceImp.class);
    RecipeServiceImp recipeServiceImp;



/*    static {
        MyElement MyElement = new MyElement( "suger", 1);
        MyElement MyElement2 = new MyElement( "flor", 10);
        MyElement MyElement3 = new MyElement( " Brown suger", 1);
        MyElement MyElement4 = new MyElement( "vanilla", 2);

        MyElement myElementList [] = {MyElement, MyElement2, MyElement3 ,MyElement4};
    }*/

/*    public static Map<String, Ingredient> ingredientRepo = new HashMap<>();


    static {

        Ingredient ingredient1 = new Ingredient("1", "suger", 1);
        Ingredient ingredient2 = new Ingredient("2", "flor", 10);
        Ingredient ingredient11 = new Ingredient("10", " Brown suger", 1);
        Ingredient ingredient22 = new Ingredient("110", "vanilla", 2);


        ingredientRepo.put(ingredient1.getId(), ingredient1);
        ingredientRepo.put(ingredient2.getId(), ingredient2);
        ingredientRepo.put(ingredient11.getId(), ingredient11);
        ingredientRepo.put(ingredient22.getId(), ingredient22);
    }*/


/*    private MyElement[] myElementList;


    public Collection<Element> getElements(){
        return null;
    }*/



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


    public void controlIngredientQuantity() {
        List<Ingredient> newListOFDuplicateIngredient = new ArrayList<>();
        Collection<Ingredient> ingredients = getIngredients();

        List<Ingredient> listDuplicateIngredient = listDuplicateIngredient(ingredients);

        for (Ingredient ingredient : listDuplicateIngredient) {
            Ingredient newIngredient = new Ingredient(ingredient.getName(), sumOfSameQuantity(listDuplicateIngredient, ingredient));
            newListOFDuplicateIngredient.add(newIngredient);
            deleteIngredient(ingredient.getId());
        }

        for (Ingredient newIngredient : newListOFDuplicateIngredient) {
            createIngredient(new Ingredient(
                    String.valueOf(new Random().nextInt()),
                    newIngredient.getName(),
                    newIngredient.getQuantity()));
        }

        List<Ingredient> listResult = listDuplicateIngredient(ingredients);
        removeDuplicate(listResult);
    }

    public void removeDuplicateByLog(List<Ingredient> collect) {
        for (int i = 0; i < collect.size(); i++) {
            for (int j = 0; j < i; j++) {
                logger.info("i" + i +  " " +"j" + j);
                logger.info(collect.get(i).getName() + "/n" + collect.get(j).getName());

                System.out.println(collect.get(i).getName().equalsIgnoreCase(collect.get(j).getName()));
                System.out.println((collect.get(i).getQuantity().compareTo(collect.get(j).getQuantity())==0));
                System.out.println((i!=j));
                if ((collect.get(i).getName().equalsIgnoreCase(collect.get(j).getName())) &&
                        (collect.get(i).getQuantity().compareTo(collect.get(j).getQuantity())==0)
                        && (i!=j)){
                    logger.debug("*******remove ing*****");
                    Ingredient ingredient = collect.get(i);
                    deleteIngredient(ingredient.getId());
                }
            }
        }
    }
    public void removeDuplicate(List<Ingredient> listIngredient) {
        for (int i = 0; i < listIngredient.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((listIngredient.get(i).getName().equalsIgnoreCase(listIngredient.get(j).getName())) &&
                        (listIngredient.get(i).getQuantity().compareTo(listIngredient.get(j).getQuantity())==0)
                        && (i!=j)){
                    Ingredient ingredient = listIngredient.get(i);

                    if(ingredient != null){
                        deleteIngredient(ingredient.getId());}
                }
            }
        }
    }
    public Integer sumOfSameQuantity(List<Ingredient> ingredients, Ingredient ingredient){
        return ingredients.stream()
                .filter(customer -> ingredient.getName().equals(customer.getName())).map(x -> x.getQuantity()).reduce(0, Integer::sum);

    }
    public List<Ingredient> listDuplicateIngredient(Collection<Ingredient> ingredients) {
        return ingredients.stream()
                .collect(Collectors.groupingBy(Ingredient:: getName))
                .entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }


}
