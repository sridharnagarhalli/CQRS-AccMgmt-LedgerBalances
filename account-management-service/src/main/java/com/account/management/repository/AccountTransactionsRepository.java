package com.account.management.repository;

import com.account.management.entity.Account;
import com.account.management.entity.AccountTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions, Long>  {
}
