package com.assignment.account.management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@Builder
public class AccountSearchResponse {

    private Long accountId;

    private BigDecimal balance;

    private String status;

    private String accountHolderName;


}
