package com.juliak.BankingApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliak.BankingApp.model.Account;
import com.juliak.BankingApp.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("123456", "Julia K", new BigDecimal("1000"));
        account.setId(1L); // Ensure the account has an ID
    }

    @Test
    void whenPostRequestToAccountsAndValidAccount_thenCorrectResponse() throws Exception {
        given(accountService.createNewAccount(any())).willReturn(account);

        mockMvc.perform(post("/api/accounts/")
                        .content(asJsonString(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountHolderName").value("Julia K"));
    }

    @Test
    void whenGetRequestToAccounts_thenCorrectResponse() throws Exception {
        given(accountService.getAllAccounts()).willReturn(List.of(account));

        mockMvc.perform(get("/api/accounts/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountHolderName").value("Julia K"));
    }

    // Utility method to convert object into JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Additional tests for other endpoints...
}
