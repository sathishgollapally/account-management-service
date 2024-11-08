package com.assignment.account.management.repository;

import com.assignment.account.management.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByAccountIdAndType(Long accountId, String type, Pageable pageable);

    Page<Transaction> findByAccountIdAndTimestampBetween(Long accountId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Transaction> findByAccountIdAndTypeAndTimestampBetween(Long accountId, String type, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.accountId = :accountId AND t.type = 'out' AND DATE(t.timestamp) = :date")
    BigDecimal findTotalWithdrawalsForToday(@Param("accountId") Long accountId, @Param("date") LocalDate date);
}
