package model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler {

    public static void saveTransactions(List<Transaction> transactions, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("Tipo,Data,Montante,Categoria\n");
            for (Transaction transaction : transactions) {
                writer.write(String.format("%s,%s,%.2f,%s\n", 
                           transaction.getType(), transaction.getDate(), transaction.getAmount(), transaction.getCategory()));
            }
        }
    }

    public static List<Transaction> loadTransactions(String filePath) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath))) {
            return transactions; 
        }

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line = reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String type = fields[0];
                LocalDate date = LocalDate.parse(fields[1]);
                double amount = Double.parseDouble(fields[2]);
                String category = fields[3];

                Transaction transaction = "Entrada".equals(type) ?
                        new Income(amount, date, category) :
                        new Expense(amount, date, category);
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}

