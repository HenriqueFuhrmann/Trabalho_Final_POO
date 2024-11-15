package model;

import java.time.LocalDate;

public class Income extends FinancialEntry {
    public Income(double amount, LocalDate date, String category) {
        super(amount, date, category);
    }

    @Override
    public String getType() {
        return "Entrada";
    }
}
