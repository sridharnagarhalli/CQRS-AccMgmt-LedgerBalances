package com.account.management.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(setterPrefix = "with")
public class CreateAccountDto {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String niNumber;
    @NonNull
    private Double depositInitialAmount;
    private Double balance;
    @NonNull
    private AddressDto addressDto;
}
