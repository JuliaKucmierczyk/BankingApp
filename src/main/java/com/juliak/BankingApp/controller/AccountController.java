package com.juliak.BankingApp.controller;

import com.juliak.BankingApp.model.Account;
import com.juliak.BankingApp.model.Transaction;
import com.juliak.BankingApp.service.AccountService;
import com.juliak.BankingApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    // Create a new account
    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = accountService.createNewAccount(account);
        return ResponseEntity.ok(newAccount);
    }

    // Delete an account
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    // Get details for a specific account
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Get all accounts
    @GetMapping("/")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Deposit money
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account account = accountService.deposit(id, amount);
        return ResponseEntity.ok(account);
    }

    // Withdraw money
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Account account = accountService.withdraw(id, amount);
        return ResponseEntity.ok(account);
    }

    // Check balance
    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> checkBalance(@PathVariable Long id) {
        BigDecimal balance = accountService.checkBalance(id);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsForAccount(@PathVariable Long id) {
        List<Transaction> transactions = transactionService.getTransactionsForAccount(id); // Use the injected instance
        return ResponseEntity.ok(transactions);
    }


}
