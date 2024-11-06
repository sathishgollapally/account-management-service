package com.assignment.account.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table
@Setter
@Getter
public class Account extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "accountId", nullable = false)
    private Long accountId;

    private BigDecimal initialBalance;

    private BigDecimal currentBalance;

    private String status;

    private String accountHolderName;


}
