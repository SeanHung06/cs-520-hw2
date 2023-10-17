package model;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {
    private final double amount;

    public AmountFilter(String amount) {
        this.amount =Double.parseDouble(amount);
    }
    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getAmount() == amount).collect(Collectors.toList());
    }
    
}
