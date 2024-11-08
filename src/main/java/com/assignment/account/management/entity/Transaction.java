package com.assignment.account.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    private Long accountId;
    private BigDecimal amount;
    private String type; // "in" for credit, "out" for debit
    private LocalDateTime timestamp;
    private BigDecimal balanceAfterTransaction;
    private boolean flaggedForReview;

}
