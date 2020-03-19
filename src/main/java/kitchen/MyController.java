package kitchen;

import dao.Ingredient;
import dao.Recipe;
import exception.ProductNotfoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
public class MyController {

    @Autowired
    RecipeService service;


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
    public ResponseEntity<Object> createRecipeYummy(@RequestBody Recipe product) {
        service.createRecipe(product);
        return new ResponseEntity<>("Yummy", HttpStatus.CREATED);
    }


    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public ResponseEntity<Object> getInventory() {
        Collection<Ingredient> ingredientsService = service.getIngredients();
        for (Ingredient ingredient : ingredientsService) {
            if(ingredient.getQuantity() == 0)
                service.removeIngredient(ingredient.getId(), ingredient);
        }
        return new ResponseEntity<>(ingredientsService, HttpStatus.OK);
    }

    @RequestMapping(value = "/inventory/fill", method = RequestMethod.POST)
    public ResponseEntity<Object> createIngredients(@RequestBody Ingredient ingredient) {

        if(ingredient.getQuantity() <= 0)
            return  new ResponseEntity<>("Rejected", HttpStatus.NOT_ACCEPTABLE);


        Collection<Ingredient> ingredientsService = service.getIngredients();
        service.createIngredient(ingredient);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }


}
