package com.ledger.balances.repository;

import com.ledger.balances.entity.LedgerBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LedgerBalanceRepository extends JpaRepository<LedgerBalance, Long> {
    List<LedgerBalance> findByTimestampLessThanEqual(Timestamp timestamp);

}

