package dao;

public class MyElement {

    private  String name; // name, e.g. "Sugar"
    private  Integer quantity; // how much of this ingredient? Must be > 0

    public MyElement(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public MyElement(){
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
