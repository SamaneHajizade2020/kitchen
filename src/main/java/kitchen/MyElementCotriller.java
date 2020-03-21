package kitchen;

import dao.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@RestController
public class MyElementCotriller {

    static final Logger logger = Logger.getLogger(MyElementCotriller.class);

    @Autowired
    IngredientServiceImp service;

    Map<String, Ingredient> ingredientMap = new HashMap<>();
    Map<String, Integer> ingredientMapIngre = new HashMap<>();
     ArrayList<Ingredient> ingredientArrayList = new ArrayList();

    @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
    public ResponseEntity<Object> getInventory() {
        Collection<Ingredient> ingredientsService = service.getIngredients();
        for (Ingredient ingredient : ingredientsService) {
            if(ingredient.getQuantity() == 0)
                service.removeIngredient(ingredient);
        }
        return new ResponseEntity<>(ingredientsService, HttpStatus.OK);
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public ResponseEntity<Object> createIngredientWithoutLimit(@RequestBody ArrayList<Ingredient> ingredients) {

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

        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/inventory/fillIngredient", method = RequestMethod.POST)
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

        //controlIngredientQuantity(ingredients);
        controlIngredientQuantity();
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }


    @RequestMapping(value = "/ingredientsRemoveDuplicate", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteDuplicateIngredientFromList() {
        Collection<Ingredient> ingredients = service.getIngredients();

        List<Ingredient> collect = listDuplicateIngredient(ingredients);

        for (int i = 0; i < collect.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((collect.get(i).getName().equalsIgnoreCase(collect.get(j).getName())) &&
                        (collect.get(i).getQuantity().compareTo(collect.get(j).getQuantity())==0)
                        && (i!=j)){
                    Ingredient ingredient = collect.get(i);

                    if(ingredient != null){
                    service.deleteIngredient(ingredient.getId());}
                }
            }
        }
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }



    @RequestMapping(value = "/ingredientsRemoveDup", method = RequestMethod.GET)
    public ResponseEntity<Object> createIngredient() {
        Collection<Ingredient> ingredients = service.getIngredients();
        System.out.println("***********ingredients Size:**********" + ingredients.size() + "*********************");

        for (Ingredient ingredient : ingredients) {
            //System.out.println(ingredient.getId() + " " + ingredient.getName() + " " + ingredient.getQuantity());
            System.out.println(" " + ingredient.getName() + " " + ingredient.getQuantity());
        }

/*        ingredients.stream().distinct().collect(Collectors.toList());
        int size = ingredients.size();
        System.out.println("size:" + size);
        System.out.println(ingredients.stream().distinct().findAny().isPresent());

        Optional<Ingredient> ingredient = ingredients.stream().distinct().findAny();
        System.out.printf("from duplicate" + ingredient);*/

        List<Ingredient> listDuplicateIngredient = listDuplicateIngredient(ingredients);
        System.out.println("list Duplicate Ingredient size:" + listDuplicateIngredient.size());

        for (Ingredient ingredient : listDuplicateIngredient) {
            System.out.println("DUPLICATE:" + " " + ingredient.getName() + " " + ingredient.getQuantity() + " ");
        }

       // List<Ingredient> duplicateRemove =
        ingredients.stream().distinct().collect(Collectors.toList());
//        for (Ingredient ingredient : duplicateRemove) {
//            System.out.println("DUPLICATE REMOVE:" + " " + ingredient.getName() + " " + ingredient.getQuantity() + " ");
//        }
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }



    //todo: without Refactoring
    private void controlIngredientQuantity(ArrayList<Ingredient> ingredientsRefactoring) {

        Collection<Ingredient> ingredients = service.getIngredients();
        System.out.println("*********************" + ingredients.size() + "*********************");

        for (Ingredient ingredient : ingredients) {
            //System.out.println(ingredient.getId() + " " + ingredient.getName() + " " + ingredient.getQuantity());
            System.out.println(" " + ingredient.getName() + " " + ingredient.getQuantity());
        }

        List<Ingredient> listDuplicateIngredient = listDuplicateIngredient(ingredients);
        System.out.println("listDuplicateIngredient size:" + listDuplicateIngredient.size());

        for (Ingredient ingredient : listDuplicateIngredient) {
            System.out.println("DUPLICATE:" + " " + ingredient.getName() + " " + ingredient.getQuantity() + " ");
        }

        //Todo: I don't know why it does not Remove duplicate!
        List<Ingredient> duplicateRemove = listDuplicateIngredient.stream().distinct().collect(Collectors.toList());


        List<Ingredient> newlistOFDuplicateIngredient = new ArrayList<>();
        //Map<String, Ingredient> ingredientMap = new HashMap<>();

        for (Ingredient ingredient : listDuplicateIngredient) {
            //Integer integerSum = listDuplicateIngredient.stream().filter(customer -> ingredient.getName().equals(customer.getName())).map(x -> x.getQuantity()).reduce(0, Integer::sum);
            Integer integerSum = sumOfSameQuantity(listDuplicateIngredient,ingredient);

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
                    }
                    service.deleteIngredient(ingredient.getId());
                }
            }
        }



        System.out.println("************new list OF Duplicate Ingredient*********" + newlistOFDuplicateIngredient.size() + "*********************");

        //Todo: I don't know why it does not Remove duplicate!
        //List<Ingredient> collect = newlistOFDuplicateIngredient.stream().distinct().collect(Collectors.toList());
       /* for (Ingredient ingredient : collect) {
            System.out.println("colect:" + " " + ingredient.getName() + " " + ingredient.getQuantity() + " ");
        }
        System.out.println("***********collectsizeAfterDistinct**********" + collect.size() + "*********************");
*/
        for (Ingredient newIngredient : newlistOFDuplicateIngredient) {
            Ingredient newIng = service.createIngredient(new Ingredient(
                    String.valueOf(new Random().nextInt()),
                    newIngredient.getName(),
                    newIngredient.getQuantity()));


            if (newIng != null) {
                System.out.println("newIng" + ":" + newIng.getId() + "" + newIng.getName() + " " + newIng.getQuantity());
            }
       }
        //removeDuplicateByLog(collect);

        List<Ingredient> collect2 = listDuplicateIngredient(ingredients);

        for (int i = 0; i < collect2.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((collect2.get(i).getName().equalsIgnoreCase(collect2.get(j).getName())) &&
                        (collect2.get(i).getQuantity().compareTo(collect2.get(j).getQuantity())==0)
                        && (i!=j)){
                    Ingredient ingredient = collect2.get(i);

                    if(ingredient != null){
                        service.deleteIngredient(ingredient.getId());}
                }
            }
        }
    }


    private void controlIngredientQuantity() {
        List<Ingredient> newListOFDuplicateIngredient = new ArrayList<>();
        Collection<Ingredient> ingredients = service.getIngredients();

        List<Ingredient> listDuplicateIngredient = listDuplicateIngredient(ingredients);

        for (Ingredient ingredient : listDuplicateIngredient) {
            Ingredient newIngredient = new Ingredient(ingredient.getName(), sumOfSameQuantity(listDuplicateIngredient, ingredient));
            newListOFDuplicateIngredient.add(newIngredient);
            service.deleteIngredient(ingredient.getId());
        }

        for (Ingredient newIngredient : newListOFDuplicateIngredient) {
            service.createIngredient(new Ingredient(
                    String.valueOf(new Random().nextInt()),
                    newIngredient.getName(),
                    newIngredient.getQuantity()));
        }

        List<Ingredient> collect2 = listDuplicateIngredient(ingredients);
        removeDuplicateByLog(collect2);
    }


    private void removeDuplicateByLog(List<Ingredient> collect) {
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
                    service.deleteIngredient(ingredient.getId());
                }
            }
        }
    }
    private void removeDuplicate(List<Ingredient> listIngredient) {
        for (int i = 0; i < listIngredient.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((listIngredient.get(i).getName().equalsIgnoreCase(listIngredient.get(j).getName())) &&
                        (listIngredient.get(i).getQuantity().compareTo(listIngredient.get(j).getQuantity())==0)
                        && (i!=j)){
                    Ingredient ingredient = listIngredient.get(i);

                    if(ingredient != null){
                        service.deleteIngredient(ingredient.getId());}
                }
            }
        }
    }
    private Integer sumOfSameQuantity(List<Ingredient> ingredients, Ingredient ingredient){
        return ingredients.stream()
                .filter(customer -> ingredient.getName().equals(customer.getName())).map(x -> x.getQuantity()).reduce(0, Integer::sum);

    }
    private List<Ingredient> listDuplicateIngredient(Collection<Ingredient> ingredients) {
        return ingredients.stream()
                .collect(Collectors.groupingBy(Ingredient:: getName))
                .entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

}

