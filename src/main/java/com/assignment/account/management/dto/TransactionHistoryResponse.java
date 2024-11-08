package com.assignment.account.management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
public class TransactionHistoryResponse {

    private List<TransactionDetails> transactions; // List of transactions
    private int currentPage; // Current page number
    private int totalPages;  // Total number of pages
    private long totalTransactions; // Total number of transactions

}
