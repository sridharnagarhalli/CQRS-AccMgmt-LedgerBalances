package com.account.management.service.mapper;

import com.account.management.dto.AccountDto;
import com.account.management.dto.AddressDto;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.dto.LedgerBalanceDto;
import com.account.management.entity.Account;
import com.account.management.entity.Address;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class AccountMapper {

    public static AccountDto fromEntity(Account account) {
        return AccountDto.builder()
                .withAccountNumber(account.getAccountNumber())
                .withFirstName(account.getFirstName()).withLastName(account.getLastName())
                .withBalance(account.getBalance())
                .withAccountCreatedTime(account.getAccountCreatedTimestamp())
                .withLastTransactionTime(account.getLastTransactionTimestamp())
                .withAddressDto(AddressDto.builder()
                        .withAddressLine1(account.getAddress().getAddressLine1())
                        .withAddressLine2(account.getAddress().getAddressLine2())
                        .withCounty(account.getAddress().getCounty())
                        .withPostcode(account.getAddress().getPostcode())
                        .withCountry(account.getAddress().getCountry())
                        .build())
                .build();
    }

    public static Account toEntity(CreateAccountCommand createAccountCommand) {
        var accountDto = createAccountCommand.getCreateAccountDto();
        var now = Instant.now();
        var account = new Account();
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setNiNumber(accountDto.getNiNumber());
        account.setBalance(accountDto.getDepositInitialAmount());
        account.setLastTransactionTimestamp(now);
        account.setAccountCreatedTimestamp(now);
        var address = new Address();
        address.setAddressLine1(accountDto.getAddressDto().getAddressLine1());
        address.setAddressLine2(accountDto.getAddressDto().getAddressLine2());
        address.setCounty(accountDto.getAddressDto().getCounty());
        address.setPostcode(accountDto.getAddressDto().getPostcode());
        address.setCountry(accountDto.getAddressDto().getCountry());
        account.setAddress(address);
        return account;
    }

    public LedgerBalanceDto mapToLedgerBalanceDto(AccountDto accountDto) {
        return LedgerBalanceDto.builder()
                .withBalance(accountDto.getBalance())
                .withAccountNumber(accountDto.getAccountNumber())
                .withAccountName(accountDto.getLastName()+","+accountDto.getFirstName())
                .withTimestamp(accountDto.getLastTransactionTime())
                .build();
    }
}
