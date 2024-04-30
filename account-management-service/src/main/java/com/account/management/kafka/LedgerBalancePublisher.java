package com.account.management.kafka;

import com.account.management.dto.LedgerBalanceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LedgerBalancePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendMessage(LedgerBalanceDto ledgerBalanceDto) {
        var ledgerBalanceJson = objectMapper.writeValueAsString(ledgerBalanceDto);
        kafkaTemplate.send("ledger-balance-event-topic", ledgerBalanceJson);
    }
}
