package dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ingredient {

    @JsonIgnore()
    private String id;

    private  String name; // name, e.g. "Sugar"
    private  Integer quantity; // how much of this ingredient? Must be > 0

    public Ingredient(String name, Integer quantity){
        this.name= name;
        this.quantity = quantity;
    }

    public Ingredient(String id, String name, Integer quantity) {
       this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Ingredient(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
