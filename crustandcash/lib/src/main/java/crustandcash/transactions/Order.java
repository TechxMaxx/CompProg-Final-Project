package crustandcash.transactions;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String itemName;
    private Map<String, Integer> ingredients = new HashMap<>();

    public Order(String itemName) {
        this.itemName = itemName;
    }

    public void addIngredient(String ingredientName, int quantity) {
        ingredients.put(ingredientName, quantity);
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public String getItemName() {
        return itemName;
    }
}