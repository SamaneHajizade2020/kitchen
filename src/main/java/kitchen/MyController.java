package kitchen;

import dao.Ingredient;
import dao.MyElement;
import dao.Recipe;
import exception.ProductNotfoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.Element;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.logging.log4j.ThreadContext.isEmpty;


@RestController
public class MyController {

    @Autowired
    RecipeService service;

/*    @Autowired
    ElementService elementService;*/


/*
    @Autowired
    private ElementRepository elementRepository;
*/


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "/recipes")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(service.getRecipes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProductById(@PathVariable("id") String id) {
        if(!RecipeServiceImp.productRepo.containsKey(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404"
            );
        return new ResponseEntity<>(service.getRecipes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Recipe product) {
        service.updateRecipe(id, product);
        return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        if(!RecipeServiceImp.productRepo.containsKey(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404"
            );
            //throw new ProductNotfoundException();
        service.deleteRecipe(id);
        return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/recipes/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createRecipe(@RequestBody Recipe product) {
        service.createRecipe(product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }


    @RequestMapping(value = "/recipe/{id}" , method = RequestMethod.PATCH)
    public ResponseEntity<Object> partialUpdateName(@RequestBody Recipe partialUpdate, @PathVariable("id") String id) {
        if(!RecipeServiceImp.productRepo.containsKey(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "404"
            );

        if(Strings.isNotEmpty(partialUpdate.getId()) || partialUpdate.getId() =="")
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, " Please note that it shall not be allowed to change the id-property of a recipe."
            );
        service.save(id, partialUpdate);
        return new ResponseEntity<Object>("Product is updated successsfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/recipes/{id}/make", method = RequestMethod.POST)
    public ResponseEntity<Object> createRecipeYummy(@RequestBody Recipe product, @PathVariable("id") String id) {

        try{
            product.setId(id);
            service.createRecipe(product);
        }catch (ResponseStatusException e){
            new ResponseEntity<>("403", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Yummy", HttpStatus.CREATED);
    }


/*    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public ResponseEntity<Object> getInventory() {
        Collection<Ingredient> ingredientsService = service.getIngredients();
        for (Ingredient ingredient : ingredientsService) {
            if(ingredient.getQuantity() == 0)
                service.removeIngredient(ingredient);
        }
        return new ResponseEntity<>(ingredientsService, HttpStatus.OK);
    }*/

/*    @RequestMapping(value = "/element", method = RequestMethod.GET)
    public ResponseEntity<Object> getElement() {
        List<MyElement> myElements = elementRepository.findAll();
        for (MyElement myElement : myElements) {
            if(myElement.getQuantity() == 0)
                elementRepository.delete(myElement);
        }
        return new ResponseEntity<>(myElements, HttpStatus.OK);
    }*/

/*    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public ResponseEntity<Object> createIngredients(@RequestBody Ingredient ingredient) {
        service.createIngredient(ingredient);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }*/

    @RequestMapping(value = "/inventory/fill", method = RequestMethod.POST)
    public ResponseEntity<Object> createIngredient(@RequestBody ArrayList<Ingredient> ingredients) {

        System.out.println(ingredients.size() + "************");
        for (Ingredient ingredient : ingredients) {

            if(ingredient.getQuantity() <= 0) {
                return  new ResponseEntity<>("Rejected cause of zero or negative quantity", HttpStatus.NOT_ACCEPTABLE);
            }

            if(ingredient.getId() == null) {
                ingredient.setId(String.valueOf(new Random().nextInt()));
            }

             service.createIngredient(ingredient);
        }

        controlIngredientQuantity();
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    private void controlIngredientQuantity() {

        Collection<Ingredient> ingredients = service.getIngredients();
        System.out.println("*********************" + ingredients.size() + "*********************");

        for (Ingredient ingredient : ingredients) {
            //System.out.println(ingredient.getId() + " " + ingredient.getName() + " " + ingredient.getQuantity());
            System.out.println(" " + ingredient.getName() + " " + ingredient.getQuantity());
        }

        List<Ingredient> newlistOFDuplicateIngredient = new ArrayList<>();
        Map<String, Ingredient> ingredientMap = new HashMap<>();

        List<Ingredient> listDuplicateIngredient = listDuplicateIngredient(ingredients);
        System.out.println("listDuplicateIngredient size:" + listDuplicateIngredient.size());

        for (Ingredient ingredient : listDuplicateIngredient) {
            System.out.println("DUPLICATE:" + " " + ingredient.getName() + " " + ingredient.getQuantity() + " ");
        }

        for (Ingredient ingredient : listDuplicateIngredient) {

            Integer integerSum = listDuplicateIngredient.stream()
                    .filter(customer -> ingredient.getName().equals(customer.getName())).map(x -> x.getQuantity()).reduce(0, Integer::sum);

            if (!listDuplicateIngredient.isEmpty()) {
                if (listDuplicateIngredient.contains(ingredient)) {

                    Ingredient newIngredient = new Ingredient(
                            //String.valueOf(new Random().nextInt()),
                            ingredient.getName(),
                            integerSum);

                    System.out.println("newINGREDIENT: " + " " + newIngredient.getName() + " " + newIngredient.getQuantity());

                    for (Ingredient y : newlistOFDuplicateIngredient) {
                        System.out.println("newINGREDIENT: " + " " + y.getName() + " " + y.getQuantity());
                    }

                    if (!newlistOFDuplicateIngredient.contains(newIngredient)) {
                        System.out.println("************ newlistOFDuplicateIngredient doesnot contain ");
                        newlistOFDuplicateIngredient.add(newIngredient);
                        ingredientMap.put(newIngredient.getId(), newIngredient);
                    }
                    service.deleteIngredient(ingredient.getId());
                }
            }

        }
        System.out.println("************newlistOFDuplicateIngredient*********" + newlistOFDuplicateIngredient.size() + "*********************");

        List<Ingredient> collect = newlistOFDuplicateIngredient.stream().distinct().collect(Collectors.toList());
        for (Ingredient ingredient : collect) {
            System.out.println("colect:" + " " + ingredient.getName() + " " + ingredient.getQuantity() + " ");
        }
        System.out.println("***********collectsizeAfterDistinct**********" + collect.size() + "*********************");


        for (Ingredient newIngredient : collect) {
            Ingredient newIng = service.createIngredient(newIngredient);
            /*Ingredient newIng = service.createIngredient( new Ingredient(
                    String.valueOf(new Random().nextInt()),
                    newIngredient.getName(),
                    newIngredient.getQuantity()));
*/
            if (newIng != null) {
                System.out.println("newIng" + ":" + newIng.getId() + "" + newIng.getName() + " " + newIng.getQuantity());
            }
        }

        removeDuplicate(collect);
    }

    private List<Ingredient> listDuplicateIngredient(Collection<Ingredient> ingredients) {
        return ingredients.stream()
                .collect(Collectors.groupingBy(Ingredient::getName))
                .entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    private void removeDuplicate(List<Ingredient> collect) {
        for (int i = 0; i < collect.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((collect.get(i).getName().equalsIgnoreCase(collect.get(j).getName())) &&
                        (collect.get(i).getQuantity().compareTo(collect.get(j).getQuantity()) == 0)
                        && (i != j)) {
                    Ingredient ingredient = collect.get(i);

                    if (ingredient != null) {
                        service.deleteIngredient(ingredient.getId());
                    }
                }
            }
        }
    }



    @RequestMapping(value = "/recipes/get-count-by-recipe", method = RequestMethod.GET)
    public ResponseEntity<Object> getCountByRecipe() {
        Collection<Recipe> recipes = service.getRecipes();
        Collection<Ingredient> ingredients = service.getIngredients();

        System.out.printf("size of recipe" + recipes.size() + " " );

        for (Recipe recipe : recipes) {
            System.out.println( recipe.getId() + " " + recipe.getName() + recipe.getInstructions() + " " + recipe.getIngredients().size());
            List<Ingredient> ingredients1 = recipe.getIngredients();
            for (Ingredient ingredient : ingredients1) {
                System.out.println( " " + ingredient.getName() + " " + ingredient.getQuantity());
            }
        }

        System.out.printf("Size of ing" + ingredients.size());
        for (Ingredient ingredient : ingredients) {
            System.out.println( " " + ingredient.getName() + " " + ingredient.getQuantity());
        }

        for (Recipe recipe : recipes) {
            List<Ingredient> ingredientListOfRecipe = recipe.getIngredients();

            List<Ingredient> collect =
                    ingredientListOfRecipe.stream()
                    .filter(ingredients::contains)
                    .collect(Collectors
                            .toList());


            Integer div = collect.stream().map(x -> x.getQuantity()).reduce(0, (a, b) -> a / b);
            //Integer div = collect.stream().map(x -> x.getQuantity()).reduce(0, Integer::divideUnsigned);
            System.out.println("div:"  + " " + div);

        if(!collect.isEmpty()) {
            for (Ingredient x : collect) {
                System.out.println(x.getId() + " " + x.getName() + " " + x.getQuantity() + " ");
            }
        }else
                System.out.println("collection list is empty!");
        }

        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }


}
