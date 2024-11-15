package model;

import java.time.LocalDate;

public interface Transaction {
    double getAmount();
    LocalDate getDate();
    String getCategory();
    String getType(); 
}
