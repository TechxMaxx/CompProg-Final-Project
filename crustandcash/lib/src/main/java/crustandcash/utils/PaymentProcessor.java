package crustandcash.utils;

import crustandcash.transactions.Transaction;

public class PaymentProcessor {
    public static void processPayment(Transaction transaction, String method, double amount) {
        System.out.println("Processing payment of $" + amount + " via " + method);
        transaction.markAsPaid();
    }
}