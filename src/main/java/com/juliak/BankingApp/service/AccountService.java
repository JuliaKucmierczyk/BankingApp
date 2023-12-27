package com.juliak.BankingApp.service;

import com.juliak.BankingApp.model.Account;
import com.juliak.BankingApp.model.Transaction;
import com.juliak.BankingApp.repository.AccountRepository;
import com.juliak.BankingApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Method to create a new account
    public Account createAccount(Account account) {
        // Set initial balance to zero if not provided
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        return accountRepository.save(account);
    }

    // Method to deposit money into an account
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        // Record transaction
        Transaction transaction = new Transaction(account, amount, Transaction.TransactionType.DEPOSIT, new Date());
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    // Method to withdraw money from an account
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        BigDecimal newBalance = account.getBalance().subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(newBalance);

        // Record transaction
        Transaction transaction = new Transaction(account, amount, Transaction.TransactionType.WITHDRAWAL, new Date());
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    // Method to check account balance
    public BigDecimal checkBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    // Method to fetch all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Method to fetch an account by ID
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}
