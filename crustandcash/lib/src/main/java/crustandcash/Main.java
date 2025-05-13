package com.crustandcash;

import crustandcash.inventory.InventoryManager;
import crustandcash.transactions.Order;
import crustandcash.transactions.SalesReport;
import crustandcash.transactions.Transaction;
import crustandcash.utils.PaymentProcessor;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Initialize inventory manager
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.addIngredient("Tomato", 50);
        inventoryManager.addIngredient("Cheese", 20);
        inventoryManager.addIngredient("Dough", 30);

        Scanner scanner = new Scanner(System.in);
        List<Order> currentOrders = new ArrayList<>();

        System.out.println("Welcome to Crust and Cash!");

        while (true) {
            // Show expanded menu
            System.out.println("\nMenu:");
            System.out.println("1. Classic Margherita (3x Tomato, 2x Cheese, 1x Dough) - $15.00");
            System.out.println("2. Double Cheese Margherita (2x Tomato, 3x Cheese, 1x Dough) - $16.00");
            System.out.println("3. Pizza Pepperoni (3x Tomato, 2x Cheese, 1x Dough) - $15.00");
            System.out.println("4. Cheese Lover's (1x Tomato, 4x Cheese, 1x Dough) - $14.00");
            System.out.println("5. Tomato Basil (4x Tomato, 1x Cheese, 1x Dough) - $13.00");
            System.out.println("6. \nProceed to Checkout");
            System.out.println("7. Exit");

            // Get user choice
            System.out.print("Please select an option (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 7) {
                if (confirmExit(scanner)) {
                    System.out.println("Thank you for visiting Crust and Cash! Goodbye!");
                    break;
                } else {
                    continue; // User chose not to exit
                }
            } else if (choice == 6) {
                if (currentOrders.isEmpty()) {
                    System.out.println("No items in your order. Please select a menu item first.");
                    continue;
                }
                // Proceed to checkout
                checkout(currentOrders, inventoryManager, scanner);
                currentOrders.clear();
                continue;
            }

            Order order;
            switch (choice) {
                case 1:
                    order = new Order("Classic Margherita");
                    order.addIngredient("Tomato", 3);
                    order.addIngredient("Cheese", 2);
                    order.addIngredient("Dough", 1);
                    break;
                case 2:
                    order = new Order("Double Cheese Margherita");
                    order.addIngredient("Tomato", 2);
                    order.addIngredient("Cheese", 3);
                    order.addIngredient("Dough", 1);
                    break;
                case 3:
                    order = new Order("Pizza Pepperoni");
                    order.addIngredient("Tomato", 3);
                    order.addIngredient("Cheese", 2);
                    order.addIngredient("Dough", 1);
                    break;
                case 4:
                    order = new Order("Cheese Lover's");
                    order.addIngredient("Tomato", 1);
                    order.addIngredient("Cheese", 4);
                    order.addIngredient("Dough", 1);
                    break;
                case 5:
                    order = new Order("Tomato Basil");
                    order.addIngredient("Tomato", 4);
                    order.addIngredient("Cheese", 1);
                    order.addIngredient("Dough", 1);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            // Check inventory before adding to order
            if (!inventoryManager.canFulfillOrder(order)) {
                System.out.println("\nSorry, we don't have enough ingredients for this pizza right now.");
                showCurrentInventory(inventoryManager);
                continue;
            }

            // Add order to current orders
            currentOrders.add(order);
            System.out.println(order.getItemName() + " added to your order!");
            
            // Show current order summary with quantities
            System.out.println("\nCurrent Order:");
            Map<String, Integer> itemCounts = new HashMap<>();
            for (Order o : currentOrders) {
                itemCounts.put(o.getItemName(), itemCounts.getOrDefault(o.getItemName(), 0) + 1);
            }
            
            double total = calculateTotal(itemCounts);
            for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
                double itemPrice = getItemPrice(entry.getKey());
                System.out.printf("%d x %s - $%.2f%n", entry.getValue(), entry.getKey(), entry.getValue() * itemPrice);
            }
            System.out.printf("Total: $%.2f%n", total);
        }

        scanner.close();
    }

    private static double getItemPrice(String itemName) {
        switch (itemName) {
            case "Classic Margherita":
            case "Pizza Pepperoni":
                return 15.00;
            case "Double Cheese Margherita":
                return 16.00;
            case "Cheese Lover's":
                return 14.00;
            case "Tomato Basil":
                return 13.00;
            default:
                return 0.00;
        }
    }

    private static double calculateTotal(Map<String, Integer> itemCounts) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            total += entry.getValue() * getItemPrice(entry.getKey());
        }
        return total;
    }

    private static boolean confirmExit(Scanner scanner) {
        while (true) {
            System.out.print("Are you sure you want to exit? (Y/N): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    private static void showCurrentInventory(InventoryManager inventoryManager) {
        System.out.println("\nCurrent Inventory Status:");
        System.out.println("- Tomato: " + inventoryManager.getInventoryStatus().get("Tomato"));
        System.out.println("- Cheese: " + inventoryManager.getInventoryStatus().get("Cheese"));
        System.out.println("- Dough: " + inventoryManager.getInventoryStatus().get("Dough"));
    }

    private static void checkout(List<Order> orders, InventoryManager inventoryManager, Scanner scanner) {
        System.out.println("\n--- Checkout ---");
        System.out.println("Your Order Summary:");
        
        Map<String, Integer> itemCounts = new HashMap<>();
        for (Order o : orders) {
            itemCounts.put(o.getItemName(), itemCounts.getOrDefault(o.getItemName(), 0) + 1);
        }
        
        double total = calculateTotal(itemCounts);
        for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
            double itemPrice = getItemPrice(entry.getKey());
            System.out.printf("%d x %s - $%.2f%n", entry.getValue(), entry.getKey(), entry.getValue() * itemPrice);
        }
        System.out.printf("Total Amount: $%.2f%n", total);

        while (true) {
            System.out.print("\nProceed with payment? (Y/N): ");
            String paymentResponse = scanner.nextLine().trim().toLowerCase();
            
            if (paymentResponse.equals("y") || paymentResponse.equals("yes")) {
                try {
                    // Update inventory for all orders
                    for (Order order : orders) {
                        inventoryManager.updateInventory(order);
                    }
                    
                    // Process payment
                    Transaction transaction = new Transaction(orders.get(0));
                    PaymentProcessor.processPayment(transaction, "CREDIT_CARD", total);
                    
                    // Generate sales report
                    SalesReport.generateReport(transaction);
                    System.out.println("Payment processed successfully! Thank you for your order!");
                    break;
                } catch (IllegalStateException ex) {
                    System.out.println("Payment failed: " + ex.getMessage());
                    break;
                }
            } else if (paymentResponse.equals("n") || paymentResponse.equals("no")) {
                System.out.println("Order cancelled. Please visit us again!");
                break;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }
}