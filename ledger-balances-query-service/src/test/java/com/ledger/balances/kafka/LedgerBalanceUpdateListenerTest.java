package com.ledger.balances.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ledger.balances.dto.LedgerBalanceDto;
import com.ledger.balances.repository.LedgerBalanceRepository;
import com.ledger.balances.service.mapper.LedgerBalanceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LedgerBalanceUpdateListenerTest {

    @Mock
    private LedgerBalanceRepository ledgerBalanceRepository;

    @Mock
    private ObjectMapper mockedObjectMapper;

    @InjectMocks
    private LedgerBalanceUpdateListener ledgerBalanceUpdateListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessMessage() throws Exception {
        LedgerBalanceDto ledgerBalanceDto = LedgerBalanceDto.builder()
                .withAccountName("Sample Account")
                .withAccountNumber("1234567890")
                .withBalance(1000.0)
                .withTimestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String ledgerBalanceJson = objectMapper.writeValueAsString(ledgerBalanceDto);

        when(mockedObjectMapper.readValue(ledgerBalanceJson, LedgerBalanceDto.class))
                .thenReturn(ledgerBalanceDto);

        ledgerBalanceUpdateListener.processMessage(ledgerBalanceJson);

        verify(ledgerBalanceRepository).save(LedgerBalanceMapper.toEntity(ledgerBalanceDto));
    }
}