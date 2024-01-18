package com.juliak.BankingApp;

import com.juliak.BankingApp.model.Account;
import com.juliak.BankingApp.model.Transaction;
import com.juliak.BankingApp.model.User;
import com.juliak.BankingApp.repository.AccountRepository;
import com.juliak.BankingApp.repository.TransactionRepository;
import com.juliak.BankingApp.repository.UserRepository;
import com.juliak.BankingApp.service.AccountService;
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
                                   AccountService accountService,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
//            // Create a new user

//            User user = new User("John", passwordEncoder.encode("test123"));
//            user.createAccount("0938127462", "John", new BigDecimal("2000.00"));
//            user = userRepository.save(user);
//
//
//            // Withdrawing
//            account = accountService.withdraw(user.getId(), new BigDecimal("200.00"));
//            // Deposit
//            account = accountService.deposit(user.getId(), new BigDecimal("500.00"));
//
//
//            // Print out the created user and account
//            System.out.println("Created User: " + user.getUsername());
//            System.out.println("Created Account: " + account.getAccountNumber() + " with balance " + account.getBalance());
            System.out.println("Login as 'Julia' with 'test123' as password ");
        };
    }
}
