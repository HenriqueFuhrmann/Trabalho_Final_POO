package model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler {
    private static final String FILE_PATH = "transactions.csv";

    public static void saveTransactions(List<Transaction> transactions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getType() + "," + transaction.getAmount() + "," +
                             transaction.getDate() + "," + transaction.getCategory());
                writer.newLine();
            }
        }
    }

    public static List<Transaction> loadTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String type = fields[0];
                double amount = Double.parseDouble(fields[1]);
                LocalDate date = LocalDate.parse(fields[2]);
                String category = fields[3];

                if ("Income".equals(type)) {
                    transactions.add(new Income(amount, date, category));
                } else if ("Expense".equals(type)) {
                    transactions.add(new Expense(amount, date, category));
                }
            }
        }
        return transactions;
    }
}
