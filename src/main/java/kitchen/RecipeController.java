package kitchen;
import dao.Ingredient;
import dao.Recipe;
import dao.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@RestController
public class RecipeController {

    @Autowired
    RecipeServiceImp service;

    @Autowired
    IngredientServiceImp ingredientService;


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
        List<Ingredient> ingredients = product.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if(ingredient.getId() == null) {
                ingredient.setId(String.valueOf(new Random().nextInt()));
            }
        }
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


    @RequestMapping(value = "/recipes/get-count-by-recipe", method = RequestMethod.GET)
    public ResponseEntity<Object> getCountByRecipe() {
        service.getCountByRecipe(service.getRecipes(), ingredientService.getIngredients());
        return new ResponseEntity<>(service.getResult(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/recipes/optimize-total-count", method = RequestMethod.GET)
    public ResponseEntity<Object> getRecipesOptimizeTotalCount() {
        service.getCountByRecipe(service.getRecipes(), ingredientService.getIngredients());
        return new ResponseEntity<>(service.getResult(), HttpStatus.CREATED);
    }
}
