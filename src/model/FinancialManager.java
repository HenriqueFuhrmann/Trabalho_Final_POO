package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FinancialManager {
    private List<Transaction> transactions = new ArrayList<>();
    private static final String FILE_PATH = "transactions.csv"; 

    public FinancialManager() {
        carregarTransacoes();  
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public double getCurrentBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getType().equals("Entrada") ? t.getAmount() : -t.getAmount();
        }
        return balance;
    }

    public double getBalanceUpToDate(LocalDate date) {
        double balance = 0;
        for (Transaction t : transactions) {
            if (!t.getDate().isAfter(date)) {  
                balance += t.getType().equals("Entrada") ? t.getAmount() : -t.getAmount();
            }
        }
        return balance;
    }

    public List<Transaction> getTransactionsSortedByDate() {
        transactions.sort(Comparator.comparing(Transaction::getDate));
        return transactions;
    }

    public void salvarTransacoes() {
        try {
            CsvFileHandler.saveTransactions(transactions, FILE_PATH);
            System.out.println("Transacoes salvas com sucesso em " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Erro ao salvar transacoes: " + e.getMessage());
        }
    }

    private void carregarTransacoes() {
        try {
            transactions = CsvFileHandler.loadTransactions(FILE_PATH);
            System.out.println("Transacoes carregadas com sucesso de " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Erro ao carregar transacoes: " + e.getMessage());
        }
    }
}
