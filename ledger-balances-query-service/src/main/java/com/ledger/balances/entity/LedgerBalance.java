package com.ledger.balances.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "LEDGER_BALANCE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LedgerBalance {

    @Id
    @GeneratedValue
    private Long id;
    private String accountNumber;
    private String accountName;
    private Double balance;
    private Timestamp timestamp;

}