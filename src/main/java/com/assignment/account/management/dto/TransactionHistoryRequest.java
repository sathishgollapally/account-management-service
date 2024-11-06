package com.assignment.account.management.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionHistoryRequest {

    private String transactionType; // Optional filter for transaction type
    private String startDate;       // Optional filter for start date
    private String endDate;         // Optional filter for end date
    private int page;               // Pagination - page number
    private int size;               // Pagination - page size
}
