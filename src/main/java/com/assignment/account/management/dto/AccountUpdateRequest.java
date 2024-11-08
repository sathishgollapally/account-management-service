package com.assignment.account.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AccountUpdateRequest {

    @NotBlank(message = "Account holder name is required.")
    @Schema(description = "Name of the account holder", example = "James Goslings", required = true)
    private String accountHolderName;

}