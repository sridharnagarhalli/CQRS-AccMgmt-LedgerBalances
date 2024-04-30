package com.ledger.balances.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ledger.balances.dto.LedgerBalanceDto;
import com.ledger.balances.repository.LedgerBalanceRepository;
import com.ledger.balances.service.mapper.LedgerBalanceMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LedgerBalanceUpdateListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LedgerBalanceUpdateListener.class);

    private final LedgerBalanceRepository ledgerBalanceRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(topics = "ledger-balance-event-topic",groupId = "ledger-balance-group")
    public void processMessage(String ledgerBalance) {
        LOGGER.info("Received message {}", ledgerBalance);
        var ledgerBalanceDto = objectMapper.readValue(ledgerBalance, LedgerBalanceDto.class);
        ledgerBalanceRepository.save(LedgerBalanceMapper.toEntity(ledgerBalanceDto));
    }

}
