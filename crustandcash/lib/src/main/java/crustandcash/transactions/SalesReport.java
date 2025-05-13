package crustandcash.transactions;

public class SalesReport {
    public static void generateReport(Transaction transaction) {
        System.out.println("Sales Report:");
        System.out.println("Item: " + transaction.getOrder().getItemName());
        System.out.println("Paid: " + transaction.isPaid());
    }
}