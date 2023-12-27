package com.juliak.BankingApp.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // Enum for transaction type
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }

    // Constructors, Getters and Setters
    public Transaction() {
    }

    public Transaction(Account account, BigDecimal amount, TransactionType type, Date timestamp) {
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    // ... include getters and setters for all fields
}
