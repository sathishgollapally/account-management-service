package com.assignment.account.management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class AccountCreateResponse {

    private Long accountId;
    private String accountHolderName;
    private BigDecimal balance;
    private String status;

}