package com.ledger.balances.service;

import com.ledger.balances.repository.LedgerBalanceRepository;
import com.ledger.balances.dto.LedgerBalanceDto;
import com.ledger.balances.service.mapper.LedgerBalanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LedgerService {

    private final LedgerBalanceRepository ledgerBalanceRepository;

    @Autowired
    public LedgerService(LedgerBalanceRepository ledgerBalanceRepository) {
        this.ledgerBalanceRepository = ledgerBalanceRepository;
    }

    public List<LedgerBalanceDto> getBalancesForTimestamp(Timestamp timestamp) {
        var ledgerBalance = ledgerBalanceRepository.findByTimestampLessThanEqual(timestamp);
        return ledgerBalance.stream()
                .map(ledger -> LedgerBalanceMapper.toDto(ledger))
                .collect(Collectors.toList());
    }
}
