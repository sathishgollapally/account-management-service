package com.assignment.account.management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionRequest {

    @Positive(message = "amount must be a positive number.")
    private BigDecimal amount;

    @NotNull(message = "Transaction type must be specified.")
    private String type; // "in" for credit, "out" for debit

}
