package crustandcash.inventory;

import crustandcash.transactions.Order;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();

    // Add or update the quantity of an ingredient in inventory
    public void addIngredient(String ingredientName, int quantity) {
        inventory.put(ingredientName, inventory.getOrDefault(ingredientName, 0) + quantity);
    }

    // Check if the inventory can fulfill the order
    public boolean canFulfillOrder(Order order) {
        for (Map.Entry<String, Integer> entry : order.getIngredients().entrySet()) {
            String ingredientName = entry.getKey();
            int quantityNeeded = entry.getValue();

            // Check if the ingredient exists and if there's enough quantity
            if (!inventory.containsKey(ingredientName) || inventory.get(ingredientName) < quantityNeeded) {
                return false; // Not enough ingredients
            }
        }
        return true; // All ingredients are available in sufficient quantities
    }

    // Update inventory based on the order
    public void updateInventory(Order order) {
        for (Map.Entry<String, Integer> entry : order.getIngredients().entrySet()) {
            String ingredientName = entry.getKey();
            int quantityUsed = entry.getValue();

            if (inventory.containsKey(ingredientName)) {
                int newQuantity = inventory.get(ingredientName) - quantityUsed;
                if (newQuantity < 0) {
                    throw new IllegalStateException("Not enough " + ingredientName + " in inventory!");
                }
                inventory.put(ingredientName, newQuantity);
            } else {
                throw new IllegalStateException("Ingredient " + ingredientName + " not found in inventory!");
            }
        }
    }

    // Get inventory status
    public Map<String, Integer> getInventoryStatus() {
        return inventory;
    }
}