package com.ledger.balances.controller;
import com.ledger.balances.dto.LedgerBalanceDto;
import com.ledger.balances.service.LedgerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LedgerControllerTest {

    @Mock
    private LedgerService ledgerService;

    @InjectMocks
    private LedgerController ledgerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLedgerBalances() throws Exception {
        String timestampString = "2024-04-29T17:40:11";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        java.util.Date parsedDate = dateFormat.parse(timestampString);
        parsedDate.setTime((parsedDate.getTime() / 1000) * 1000);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        List<LedgerBalanceDto> mockBalances = new ArrayList<>();

        when(ledgerService.getBalancesForTimestamp(timestamp)).thenReturn(mockBalances);

        List<LedgerBalanceDto> result = ledgerController.getLedgerBalances(timestampString);

        assertEquals(mockBalances, result);
    }
}
