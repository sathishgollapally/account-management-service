package com.assignment.account.management.service;

import com.assignment.account.management.dto.*;
import com.assignment.account.management.entity.Account;
import com.assignment.account.management.entity.Transaction;
import com.assignment.account.management.exception.AccountNotFoundException;
import com.assignment.account.management.exception.InsufficientFundsException;
import com.assignment.account.management.repository.AccountRepository;
import com.assignment.account.management.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class AccountManagementService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public AccountManagementService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest) {
        Account account = new Account();

        account.setAccountHolderName(accountCreateRequest.getAccountHolderName());
        account.setInitialBalance(accountCreateRequest.getInitialBalance());
        account.setCurrentBalance(accountCreateRequest.getInitialBalance());
        account.setStatus("ACTIVE");

        Account savedAccount = accountRepository.save(account);

        return AccountCreateResponse.builder().accountId(savedAccount.getAccountId())
                .accountHolderName(savedAccount.getAccountHolderName())
                .balance(savedAccount.getCurrentBalance())
                .status(savedAccount.getStatus())
                .build();

    }

    public AccountSearchResponse getAccountDetails(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        return AccountSearchResponse.builder()
                .accountId(account.getAccountId())
                .accountHolderName(account.getAccountHolderName())
                .balance(account.getCurrentBalance())
                .status(account.getStatus())
                .build();
    }


    public void  updateAccount(Long accountId, AccountUpdateRequest accountUpdateRequest) {

            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
            account.setAccountHolderName(accountUpdateRequest.getAccountHolderName());
            accountRepository.save(account);

    }

    public void suspendAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        account.setStatus("SUSPENDED");
        accountRepository.save(account);
    }

    @Transactional
    public TransactionResponse processTransaction(Long accountId, TransactionRequest transactionRequest) {
        // Fetch the account details
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        // Check transaction type and process accordingly
        BigDecimal newBalance = account.getCurrentBalance();
        if ("in".equalsIgnoreCase(transactionRequest.getType())) {
            newBalance = newBalance.add(transactionRequest.getAmount());
        } else if ("out".equalsIgnoreCase(transactionRequest.getType())) {
            if (newBalance.compareTo(transactionRequest.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient funds to process the transaction.");
            }
            newBalance = newBalance.subtract(transactionRequest.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type. Allowed types are 'in' or 'out'.");
        }

        // Update account balance
        account.setCurrentBalance(newBalance);
        accountRepository.save(account);

        // Save the transaction in the transaction table
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getType());
        transaction.setTimestamp(LocalDateTime.parse(transactionRequest.getTimestamp()));
        transaction.setBalanceAfterTransaction(newBalance);

        // Save the transaction record
        transactionRepository.save(transaction);

        // Return transaction details
        TransactionResponse response = new TransactionResponse();
        response.setAccountId(accountId);
        response.setAmount(transactionRequest.getAmount());
        response.setType(transactionRequest.getType());
        response.setTimestamp(LocalDateTime.parse(transactionRequest.getTimestamp()));
        response.setNewBalance(newBalance);

        return response;
    }


    public TransactionHistoryResponse getTransactionHistory(Long accountId, String transactionType,
                                                            LocalDateTime startDate, LocalDateTime endDate, int page, int size) {

        return  new TransactionHistoryResponse();

    }
}
