package com.ledger.balances.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
@Builder(setterPrefix = "with")
public class LedgerBalanceDto {
    private String accountName;
    private String accountNumber;
    private double balance;
    private Timestamp timestamp;
}
