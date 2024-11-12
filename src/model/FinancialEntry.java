package model;

import java.time.LocalDate;

public abstract class FinancialEntry implements Transaction {
    protected double amount;
    protected LocalDate date;
    protected String category;

    public FinancialEntry(double amount, LocalDate date, String category) {
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getCategory() {
        return category;
    }

    // Abstract method to be implemented by Income and Expense
    public abstract String getType();
}
