package com.account.management.entity;

import com.account.management.dto.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "ACCOUNT_TRANSACTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactions {
    @Id
    @GeneratedValue
    private Long transactionId;
    private String accountNumber;
    private TransactionType transactionType;
    private Double amount;
    private Instant transactionTime;
}
