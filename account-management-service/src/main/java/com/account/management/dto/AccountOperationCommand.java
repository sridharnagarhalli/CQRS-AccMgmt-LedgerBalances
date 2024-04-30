package com.account.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountOperationCommand {
    @NonNull
    private TransactionType transactionType;
    @NonNull
    private TransactionDto transactionDto;
}
