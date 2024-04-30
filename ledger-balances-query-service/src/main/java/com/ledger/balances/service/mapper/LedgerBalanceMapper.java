package com.ledger.balances.service.mapper;

import com.ledger.balances.entity.LedgerBalance;
import com.ledger.balances.dto.LedgerBalanceDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LedgerBalanceMapper {

    public static LedgerBalanceDto toDto(LedgerBalance entity) {
        return LedgerBalanceDto.builder()
                .withAccountName(entity.getAccountName())
                .withAccountNumber(entity.getAccountNumber())
                .withBalance(entity.getBalance())
                .withTimestamp(entity.getTimestamp())
                .build();
    }

    public static LedgerBalance toEntity(LedgerBalanceDto dto) {
        var ledgerBalance = new LedgerBalance();
        ledgerBalance.setBalance(dto.getBalance());
        ledgerBalance.setAccountName(dto.getAccountName());
        ledgerBalance.setAccountNumber(dto.getAccountNumber());
        ledgerBalance.setTimestamp(dto.getTimestamp());
        return ledgerBalance;
    }
}
