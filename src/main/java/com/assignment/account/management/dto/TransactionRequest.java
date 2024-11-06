package com.assignment.account.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionRequest {

    @NotNull(message = "Amount must not be null.")
    private BigDecimal amount;

    @NotNull(message = "Transaction type must be specified.")
    private String type; // "in" for credit, "out" for debit

    @NotNull(message = "Timestamp must not be null.")
    private String timestamp;
}
