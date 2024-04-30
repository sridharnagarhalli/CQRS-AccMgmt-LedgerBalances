package com.account.management.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(setterPrefix = "with")
public class LedgerBalanceDto {
    private String accountName;
    private String accountNumber;
    private double balance;
    private Instant timestamp;
}
