
package model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class FinancialManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public double getCurrentBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getType().equals("Income") ? t.getAmount() : -t.getAmount();
        }
        return balance;
    }

    public double getBalanceUpToDate(LocalDate date) {
        double balance = 0;
        for (Transaction t : transactions) {
            if (!t.getDate().isAfter(date)) {  // Checks if transaction date is on or before the given date
                balance += t.getType().equals("Income") ? t.getAmount() : -t.getAmount();
            }
        }
        return balance;
    }
    
    public List<Transaction> getTransactionsSortedByDate() {
      transactions.sort(Comparator.comparing(Transaction::getDate));
        return transactions;
    }

    // Método para salvar transações em um arquivo CSV
    public void salvarTransacoesCSV(String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write("Tipo,Data,Montante,Categoria\n");  // Cabeçalho do arquivo CSV
            for (Transaction t : transactions) {
                writer.write(String.format("%s,%s,%.2f,%s\n", 
                           t.getType(), t.getDate(), t.getAmount(), t.getCategory()));
            }
            System.out.println("Transações salvas com sucesso em " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao salvar transações: " + e.getMessage());
        }
    }

    // Método para carregar transações de um arquivo CSV
    public void carregarTransacoesCSV(String filePath) {
        transactions.clear();  // Limpa a lista antes de carregar novos dados
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line = reader.readLine();  // Lê o cabeçalho e ignora
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                String tipo = campos[0];
                LocalDate data = LocalDate.parse(campos[1]);
                double montante = Double.parseDouble(campos[2]);
                String categoria = campos[3];

                // Cria uma transação com base no tipo
                Transaction transacao;
                if (tipo.equals("Income")) {
                    transacao = new Income(montante, data, categoria);
                } else {
                    transacao = new Expense(montante, data, categoria);
                }
                transactions.add(transacao);
            }
            System.out.println("Transações carregadas com sucesso de " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao carregar transações: " + e.getMessage());
        }
    }
}
    

