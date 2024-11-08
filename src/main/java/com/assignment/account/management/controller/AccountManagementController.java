package com.assignment.account.management.controller;

import com.assignment.account.management.dto.*;
import com.assignment.account.management.service.AccountManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Management", description = "APIs for managing user accounts and transactions")
@Slf4j
public class AccountManagementController {

    private final AccountManagementService accountManagementService;

    public AccountManagementController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }

    @PostMapping
    @Operation(summary = "Create a new account", description = "Creates a new account with an initial balance", tags = {"Account Management"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = AccountCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody @Valid AccountCreateRequest accountRequest) {
        log.debug("Entered AccountManagementController.createAccount()");
        AccountCreateResponse account = accountManagementService.createAccount(accountRequest);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    @Operation(
            summary = "Retrieve account details by accountId",
            description = "Fetches the details of an account, including current balance and status, based on the provided account ID.",
            tags = {"Account Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AccountSearchResponse.class))
            ),
            @ApiResponse(                    responseCode = "400",
                    description = "Invalid request parameters"
            ),
            @ApiResponse(responseCode = "404",
                    description = "Account not found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<AccountSearchResponse> getAccountDetails(@PathVariable @Valid Long accountId) {
        log.debug("Entered AccountManagementController.getAccountDetails()");
        AccountSearchResponse account = accountManagementService.getAccountDetails(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @PutMapping("/{accountId}")
    @Operation(summary = "Update account details by accountId", description = "Updates account holder's name and status", tags = {"Account Management"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updateAccount(
            @PathVariable Long accountId,
            @RequestBody @Valid AccountUpdateRequest accountUpdateRequest) {
        log.debug("Entered AccountManagementController.updateAccount()");
        accountManagementService.updateAccount(accountId, accountUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Suspend account by ID", description = "Marks an account as suspended without deleting it physically", tags = {"Account Management"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account suspended successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> suspendAccount(@PathVariable Long accountId) {
        log.debug("Entered AccountManagementController.suspendAccount()");
        accountManagementService.suspendAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{accountId}/transactions")
    @Operation(
            summary = "Make a transaction for the account by ID",
            description = "Process a credit or debit transaction for the given account. Ensures that the account balance cannot go negative and handles concurrency safely.",
            tags = { "Transaction Management" }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Transaction processed successfully",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters (e.g., amount, type)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Insufficient funds or concurrency conflict (transaction failed)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    public ResponseEntity<TransactionResponse> processTransaction(
            @PathVariable Long accountId,
            @RequestBody @Valid TransactionRequest transactionRequest) {
        log.debug("Entered AccountManagementController.processTransaction()");
        TransactionResponse transactionResponse = accountManagementService.processTransaction(accountId, transactionRequest);
        return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{accountId}/transactions")
    @Operation(
            summary = "Get account transaction history by account ID",
            description = "Fetch a paginated list of transactions for a specific account, with optional filters like date range and transaction type.",
            tags = { "Transaction Management" }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction history retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TransactionHistoryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    public ResponseEntity<TransactionHistoryResponse> getTransactionHistory(
            @PathVariable Long accountId,
            @RequestParam(required = false) String transactionType, // Optional filter for transaction type
            @RequestParam(required = false) LocalDateTime startDate, // Optional filter for start date
            @RequestParam(required = false) LocalDateTime endDate,   // Optional filter for end date
            @RequestParam(defaultValue = "0") int page, // Pagination - default page is 0
            @RequestParam(defaultValue = "10") int size // Pagination - default size is 10
    ) {
        log.debug("Entered AccountManagementController.getTransactionHistory()");
        TransactionHistoryResponse response = accountManagementService.getTransactionHistory(accountId, transactionType, startDate, endDate, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}




