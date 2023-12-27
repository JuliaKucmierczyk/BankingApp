package com.juliak.BankingApp.service;

import com.juliak.BankingApp.model.Account;
import com.juliak.BankingApp.repository.AccountRepository;
import com.juliak.BankingApp.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class AccountServiceTest {
    private Account account;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;






    @BeforeEach
    void setUp() {
        openMocks(this);
        // Create a new account with realistic details
        account = new Account("123456", "Julia K", new BigDecimal("1000"));
        account.setId(1L); // Ensure the account has an ID, as it would in a real scenario

        // Mock the behavior of findById to return this account for any ID
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
    }

    @Test
    void getAccount() {
        System.out.println(account.getAccountNumber());
    }

    @Test
    void checkBalance(){
        BigDecimal a = account.getBalance();
        System.out.println("Running testDeposit: " + a);
    }

    @Test
    void testDeposit() {
        BigDecimal depositAmount = new BigDecimal("500");
        accountService.deposit(account.getId(), depositAmount);
        assertEquals(new BigDecimal("1500"), account.getBalance());

        // Verify that findById was called with the correct ID
        verify(accountRepository).findById(account.getId());
    }


    @Test
    void testWithdraw() {
        BigDecimal withdrawAmount = new BigDecimal("200");
        accountService.withdraw(account.getId(), withdrawAmount);
        assertEquals(new BigDecimal("800"), account.getBalance());
    }

    @Test
    void testCheckBalance() {
        assertEquals(new BigDecimal("1000"), accountService.checkBalance(account.getId()));
    }

    // Additional tests can be added here
    @Test
    void testDeposit2() {
        System.out.println("Running testDeposit");
        BigDecimal depositAmount = new BigDecimal("500");
        accountService.deposit(account.getId(), depositAmount);
        assertEquals(new BigDecimal("1500"), account.getBalance());
    }

}
