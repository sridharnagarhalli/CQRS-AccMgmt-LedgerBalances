package com.account.management.repository;

import com.account.management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account, Long>  {

    Account findByAccountNumber(String accountNumber);
}
