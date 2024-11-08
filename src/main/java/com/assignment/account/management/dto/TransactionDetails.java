package com.assignment.account.management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class TransactionDetails {
        private Long transactionId;
        private BigDecimal amount;
        private String type; // "in" for credit, "out" for debit
        private LocalDateTime timestamp;
        private BigDecimal balanceAfterTransaction;
    }