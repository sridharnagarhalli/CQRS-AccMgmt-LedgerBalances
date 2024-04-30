package com.ledger.balances.controller;

import com.ledger.balances.dto.LedgerBalanceDto;
import com.ledger.balances.service.LedgerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@RequestMapping("/api/v1/ledger")
@RestController
public class LedgerController {

    private final LedgerService ledgerService;

    @Autowired
    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping("/balances")
    @ApiOperation(value = "Get ledger balances")
    public List<LedgerBalanceDto> getLedgerBalances(@RequestParam("timestamp") String timestamp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(timestamp);
            parsedDate.setTime((parsedDate.getTime() / 1000) * 1000);
            return ledgerService.getBalancesForTimestamp(new Timestamp(parsedDate.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
