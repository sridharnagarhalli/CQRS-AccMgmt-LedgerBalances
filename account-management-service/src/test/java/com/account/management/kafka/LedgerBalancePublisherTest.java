package com.account.management.kafka;

import static org.mockito.Mockito.*;

import com.account.management.dto.LedgerBalanceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

public class LedgerBalancePublisherTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LedgerBalancePublisher ledgerBalancePublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessage() throws Exception {
        LedgerBalanceDto ledgerBalanceDto = createMockLedgerBalanceDto();
        String ledgerBalanceJson = "{\"accountName\":\"John Doe\",\"accountNumber\":\"123456789\",\"balance\":1000.0}";
        when(objectMapper.writeValueAsString(ledgerBalanceDto)).thenReturn(ledgerBalanceJson);

        ledgerBalancePublisher.sendMessage(ledgerBalanceDto);

        verify(kafkaTemplate).send("ledger-balance-event-topic", ledgerBalanceJson);
    }

    private LedgerBalanceDto createMockLedgerBalanceDto() {
        return LedgerBalanceDto.builder()
                .withAccountName("John Doe")
                .withAccountNumber("123456789")
                .withBalance(1000.0)
                .build();
    }
}
