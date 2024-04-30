package com.account.management.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(setterPrefix = "with")
public class AccountDto {
    private String accountNumber;
    private String firstName;
    private String lastName;
    private Double balance;
    private Instant accountCreatedTime;
    private Instant lastTransactionTime;
    private AddressDto addressDto;
}
