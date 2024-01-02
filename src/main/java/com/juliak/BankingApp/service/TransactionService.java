package com.juliak.BankingApp.service;

import com.juliak.BankingApp.model.Transaction;
import com.juliak.BankingApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Method to create a new transaction
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Method to fetch all transactions for an account
    public List<Transaction> getAllTransactionsForAccount(Long accountId) {
        // Implement logic to fetch transactions based on account ID
        return null; // Replace with actual implementation
    }
}
