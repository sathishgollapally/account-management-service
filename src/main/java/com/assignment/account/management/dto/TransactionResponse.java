package com.assignment.account.management.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionResponse {

    private Long accountId;
    private BigDecimal amount;
    private String type; // "in" for credit, "out" for debit
    private LocalDateTime timestamp;
    private BigDecimal newBalance;
}
