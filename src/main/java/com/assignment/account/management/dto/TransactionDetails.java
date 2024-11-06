package com.assignment.account.management.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionDetails {
        private Long transactionId;
        private BigDecimal amount;
        private String type; // "in" for credit, "out" for debit
        private String timestamp;
        private BigDecimal balanceAfterTransaction;
    }