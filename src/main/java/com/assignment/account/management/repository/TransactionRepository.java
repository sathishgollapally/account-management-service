package com.assignment.account.management.repository;

import com.assignment.account.management.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find transactions by accountId and transactionType
    Page<Transaction> findByAccountIdAndType(Long accountId, String type, PageRequest pageRequest);

    // Find transactions by accountId and a date range (start and end)
    Page<Transaction> findByAccountIdAndTimestampBetween(Long accountId, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    // Find transactions by accountId, transactionType, and a date range
    Page<Transaction> findByAccountIdAndTypeAndTimestampBetween(Long accountId, String type, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    // Find transactions by accountId without filters
    Page<Transaction> findByAccountId(Long accountId, PageRequest pageRequest);
}
