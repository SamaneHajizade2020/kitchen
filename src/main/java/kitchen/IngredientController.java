package kitchen;

import dao.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.apache.log4j.Logger;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class IngredientController {

    static final Logger logger = Logger.getLogger(IngredientController.class);

    @Autowired
    IngredientServiceImp service;

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public ResponseEntity<Object> getInventory() {
        Collection<Ingredient> ingredientsService = service.getIngredients();
        for (Ingredient ingredient : ingredientsService) {
            if(ingredient.getQuantity() == 0)
                service.removeIngredient(ingredient);
        }
        return new ResponseEntity<>(ingredientsService, HttpStatus.OK);
    }

    @RequestMapping(value = "/createIngredient", method = RequestMethod.POST)
    public ResponseEntity<Object> createIngredientWithoutLimit(@RequestBody ArrayList<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients) {

            if(ingredient.getQuantity() <= 0) {
                return  new ResponseEntity<>("Rejected cause of zero or negative quantity", HttpStatus.NOT_ACCEPTABLE);
            }

            if(ingredient.getId() == null) {
                ingredient.setId(String.valueOf(new Random().nextInt()));
            }

            service.createIngredient(ingredient);
        }

        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/inventory/fill", method = RequestMethod.POST)
    public ResponseEntity<Object> createIngredient(@RequestBody ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {

            if(ingredient.getQuantity() <= 0) {
                return  new ResponseEntity<>("Rejected cause of zero or negative quantity", HttpStatus.NOT_ACCEPTABLE);
            }

            if(ingredient.getId() == null) {
                ingredient.setId(String.valueOf(new Random().nextInt()));
            }

            service.createIngredient(ingredient);
        }

        service.controlIngredientQuantity();

        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/ingredientsRemoveDuplicate", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteDuplicateIngredientFromList() {
        Collection<Ingredient> ingredients = service.getIngredients();

        List<Ingredient> duplicateIngredient = service.listDuplicateIngredient(ingredients);

        if(!duplicateIngredient.isEmpty())
        service.removeDuplicate(duplicateIngredient);

        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/inventory/all", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAll() {
        Collection<Ingredient> ingredientsService = service.getIngredients();
        for (Ingredient ingredient : ingredientsService) {
            service.deleteIngredient(ingredient.getId());
        }
        return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
    }


}

