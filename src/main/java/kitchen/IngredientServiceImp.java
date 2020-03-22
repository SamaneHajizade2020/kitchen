package kitchen;

import dao.Ingredient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static kitchen.RecipeServiceImp.ingredientRepo;


@Service
public class IngredientServiceImp implements IngredientService {

    static final Logger LOGGER = Logger.getLogger(IngredientServiceImp.class);

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
                LOGGER.info("i" + i +  " " +"j" + j);
                LOGGER.info(collect.get(i).getName() + "/n" + collect.get(j).getName());

                LOGGER.info(collect.get(i).getName().equalsIgnoreCase(collect.get(j).getName()));
                LOGGER.info((collect.get(i).getQuantity().compareTo(collect.get(j).getQuantity())==0));
                LOGGER.info((i!=j));
                if ((collect.get(i).getName().equalsIgnoreCase(collect.get(j).getName())) &&
                        (collect.get(i).getQuantity().compareTo(collect.get(j).getQuantity())==0)
                        && (i!=j)){
                    LOGGER.debug("*******remove ing*****");
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
