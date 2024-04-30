package com.account.management.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Data
public class TransactionDto {

    @NonNull
    private Double transactionAmount;
    @NonNull
    private String accountNumber;

}
