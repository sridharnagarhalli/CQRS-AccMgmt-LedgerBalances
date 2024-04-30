package com.account.management.service;

import com.account.management.dto.AccountDto;
import com.account.management.dto.AccountOperationCommand;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.dto.TransactionDto;
import com.account.management.dto.TransactionType;
import com.account.management.entity.Account;
import com.account.management.exception.AccountManagementException;
import com.account.management.kafka.LedgerBalancePublisher;
import com.account.management.repository.AccountRepository;
import com.account.management.repository.AddressRepository;
import com.account.management.service.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.account.management.dto.TransactionType.DEPOSIT;
import static com.account.management.dto.TransactionType.WITHDRAW;

@AllArgsConstructor
@Service
public class AccountManagementService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final LedgerBalancePublisher ledgerBalancePublisher;

    public AccountDto createAccount(CreateAccountCommand createAccountCommand) {
        var account = AccountMapper.toEntity(createAccountCommand);
        addressRepository.save(account.getAddress());
        accountRepository.save(account);
        var accountDto = AccountMapper.fromEntity(account);
        ledgerBalancePublisher.sendMessage(AccountMapper.mapToLedgerBalanceDto(accountDto));
        return accountDto;
    }

    public AccountDto transact(AccountOperationCommand accountOperationCommand) {
        var accountNumber = accountOperationCommand.getTransactionDto().getAccountNumber();
        var transactionDto = accountOperationCommand.getTransactionDto();
        var account = accountRepository.findByAccountNumber(accountNumber);
        switch (accountOperationCommand.getTransactionType()) {
            case DEPOSIT:
                validateTransaction(transactionDto, DEPOSIT, account.getBalance());
                account.setBalance(account.getBalance() + transactionDto.getTransactionAmount());
                account = accountRepository.save(account);
                var accountDto = AccountMapper.fromEntity(account);
                ledgerBalancePublisher.sendMessage(AccountMapper.mapToLedgerBalanceDto(accountDto));
                return accountDto;
            case WITHDRAW:
                validateTransaction(transactionDto, WITHDRAW, account.getBalance());
                account.setBalance(account.getBalance() - transactionDto.getTransactionAmount());
                account = accountRepository.save(account);
                var accountDtoWithdraw = AccountMapper.fromEntity(account);
                ledgerBalancePublisher.sendMessage(AccountMapper.mapToLedgerBalanceDto(accountDtoWithdraw));
                return accountDtoWithdraw;
            default:
                throw new AccountManagementException("Invalid transaction type");
        }
    }

    private void validateTransaction(TransactionDto transactionDto, TransactionType transactionType, Double accountBalance) {
        if(transactionDto.getTransactionAmount() <= 0) {
            throw new AccountManagementException("Invalid transaction. Amount should be greater than zero.");
        }
        if(WITHDRAW == transactionType && accountBalance < transactionDto.getTransactionAmount()) {
            throw new AccountManagementException("Insufficient balance.");
        }
    }
}
