package com.account.management.repository;

import com.account.management.entity.Account;
import com.account.management.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>  {
}
