package dao;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String id; // > 0
    private String name;  // name of the recipe
    private String instructions;  // instructions & howto
    private List<Ingredient> ingredients ; // list of ingredients
    //private Ingredients ingredients []; // list of ingredients


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public String getInstructions() {
          return instructions;
     }

     public void setInstructions(String instructions) {
          this.instructions = instructions;
     }

     public List<Ingredient> getIngredients() {
          return ingredients;
     }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
