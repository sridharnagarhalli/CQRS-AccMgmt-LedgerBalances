package com.ledger.balances.service;

import com.ledger.balances.dto.LedgerBalanceDto;
import com.ledger.balances.entity.LedgerBalance;
import com.ledger.balances.repository.LedgerBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LedgerServiceTest {

    @Mock
    private LedgerBalanceRepository ledgerBalanceRepository;

    @InjectMocks
    private LedgerService classUnderTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBalancesForTimestamp() {
        var id = 1l;
        var accountNumber = "accountNumber";
        var accountName = "accountName";
        var balance = 100.0;
        var timestamp = Timestamp.from(Instant.now());
        List<LedgerBalance> mockLedgerBalanceList = new ArrayList<>();
        mockLedgerBalanceList.add(new LedgerBalance(id, accountNumber, accountName, balance, timestamp));
        when(ledgerBalanceRepository.findByTimestampLessThanEqual(timestamp))
                .thenReturn(mockLedgerBalanceList);

        List<LedgerBalanceDto> result = classUnderTest.getBalancesForTimestamp(timestamp);
        var dtoReturned = result.get(0);
        assertEquals(mockLedgerBalanceList.size(), result.size());
        assertEquals(accountNumber, dtoReturned.getAccountNumber());
        assertEquals(accountName, dtoReturned.getAccountName());
        assertEquals(balance, dtoReturned.getBalance());
        assertEquals(timestamp, dtoReturned.getTimestamp());
    }

    // Add more test cases as needed
}