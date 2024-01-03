package com.juliak.BankingApp;

import com.juliak.BankingApp.model.Account;
import com.juliak.BankingApp.model.Transaction;
import com.juliak.BankingApp.model.User;
import com.juliak.BankingApp.repository.AccountRepository;
import com.juliak.BankingApp.repository.TransactionRepository;
import com.juliak.BankingApp.repository.UserRepository;
import com.juliak.BankingApp.service.TransactionService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;

@Configuration
public class DataInitializerConfig {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   AccountRepository accountRepository,
                                   TransactionService transactionService,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Create a new user
            User user = new User("julia", passwordEncoder.encode("test123"));
            user = userRepository.save(user);

            // Create a new account for the user with some initial balance
            Account account = new Account("1234567890", "Julia's Account", new BigDecimal("1000.00"));
            account = accountRepository.save(account);

            Transaction depositTransaction = new Transaction(account, new BigDecimal("500.00"), Transaction.TransactionType.DEPOSIT, new Date());
            Transaction withdrawalTransaction = new Transaction(account, new BigDecimal("200.00"), Transaction.TransactionType.WITHDRAWAL, new Date());

            // Transactions just for presentation
            transactionService.createTransaction(withdrawalTransaction);
            transactionService.createTransaction(depositTransaction);


            // Print out the created user and account
            System.out.println("Created User: " + user.getUsername());
            System.out.println("Created Account: " + account.getAccountNumber() + " with balance " + account.getBalance());
            System.out.println("Login as 'Julia' with 'test123' as password ");
        };
    }
}
