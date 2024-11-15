package model;

import java.time.LocalDate;

public class Expense extends FinancialEntry {
    public Expense(double amount, LocalDate date, String category) {
        super(amount, date, category);
    }

    @Override
    public String getType() {
        return "Despesa";
        
    }
}
