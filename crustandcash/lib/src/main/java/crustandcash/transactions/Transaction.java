    package crustandcash.transactions;

    public class Transaction {
        private Order order;
        private boolean isPaid;

        public Transaction(Order order) {
            this.order = order;
            this.isPaid = false;
        }

        public void markAsPaid() {
            this.isPaid = true;
        }

        public Order getOrder() {
            return order;
        }

        public boolean isPaid() {
            return isPaid;
        }
    }