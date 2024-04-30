package com.account.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.account.management.dto.AccountDto;
import com.account.management.dto.AccountOperationCommand;
import com.account.management.dto.AddressDto;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.dto.CreateAccountDto;
import com.account.management.dto.TransactionDto;
import com.account.management.dto.TransactionType;
import com.account.management.entity.Account;
import com.account.management.entity.Address;
import com.account.management.exception.AccountManagementException;
import com.account.management.kafka.LedgerBalancePublisher;
import com.account.management.repository.AccountRepository;
import com.account.management.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

public class AccountManagementServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private LedgerBalancePublisher ledgerBalancePublisher;

    @InjectMocks
    private AccountManagementService accountManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount() {
        var account = createMockAccountEntity();
        var address = createMockAddressEntity();
        when(addressRepository.save(any())).thenReturn(address);
        when(accountRepository.save(any())).thenReturn(account);

        CreateAccountCommand createAccountCommand = createMockCreateAccountCommand();

        AccountDto result = accountManagementService.createAccount(createAccountCommand);

        assertNotNull(result);
        assertEquals(account.getBalance(), result.getBalance());
        assertEquals(account.getFirstName(), result.getFirstName());
        assertEquals(account.getLastName(), result.getLastName());
        verify(accountRepository).save(any());
        verify(addressRepository).save(any());
        verify(ledgerBalancePublisher).sendMessage(any());
    }

    @Test
    public void testTransact_Deposit() {
        Account mockAccount = createMockAccountEntity();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(mockAccount);
        when(accountRepository.save(any())).thenReturn(mockAccount);

        AccountOperationCommand accountOperationCommand = createMockAccountOperationCommand(TransactionType.DEPOSIT, 100.0);
        AccountDto result = accountManagementService.transact(accountOperationCommand);

        assertNotNull(result);
        verify(accountRepository).save(any());
        verify(accountRepository).findByAccountNumber(any());
    }

    @Test
    public void testTransact_Withdraw() {
        Account mockAccount = createMockAccountEntity();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(mockAccount);
        when(accountRepository.save(any())).thenReturn(mockAccount);

        AccountOperationCommand accountOperationCommand = createMockAccountOperationCommand(TransactionType.WITHDRAW, 10.0);
        AccountDto result = accountManagementService.transact(accountOperationCommand);
        assertNotNull(result);
        verify(accountRepository).save(any());
        verify(accountRepository).findByAccountNumber(any());
    }

    @Test
    public void testTransact_Withdraw_Exception() {
        Account mockAccount = createMockAccountEntity();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(mockAccount);

        AccountOperationCommand accountOperationCommand = createMockAccountOperationCommand(TransactionType.WITHDRAW, -100.0);
        var exception = assertThrows(AccountManagementException.class,
                () -> accountManagementService.transact(accountOperationCommand));
        assertTrue(exception.getMessage().contains("Invalid transaction. Amount should be greater than zero."));
        verify(accountRepository).findByAccountNumber(any());
    }

    @Test
    public void testTransact_Withdraw_InsufficientBalanceException() {
        Account mockAccount = createMockAccountEntity();
        mockAccount.setBalance(200.0);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(mockAccount);

        AccountOperationCommand accountOperationCommand = createMockAccountOperationCommand(TransactionType.WITHDRAW, 300.0);
        var exception = assertThrows(AccountManagementException.class,
                () -> accountManagementService.transact(accountOperationCommand));
        assertTrue(exception.getMessage().contains("Insufficient balance"));
        verify(accountRepository).findByAccountNumber(any());
    }

    private Address createMockAddressEntity() {
        Address address = new Address();
        address.setAddress_id(1L);
        address.setAddressLine1("123 Main St");
        address.setAddressLine2("Apt 101");
        address.setPostcode("12345");
        address.setCounty("County");
        address.setCountry("Country");
        return address;
    }
    private Account createMockAccountEntity() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123456789");
        account.setNiNumber("AB123456C");
        account.setFirstName("John");
        account.setLastName("Doe");
        account.setBalance(100.0);
        account.setAccountCreatedTimestamp(Instant.now());
        account.setLastTransactionTimestamp(Instant.now());
        account.setAddress(createMockAddressEntity());
        return account;
    }

    private CreateAccountCommand createMockCreateAccountCommand() {
        CreateAccountCommand cmd = new CreateAccountCommand();
        var createdAccountDto = CreateAccountDto.builder()
                .withFirstName("John")
                .withLastName("Doe")
                .withNiNumber("12345")
                .withDepositInitialAmount(100.0)
                .withAddressDto(AddressDto.builder()
                        .withAddressLine1("123 Main St")
                        .withCounty("GB")
                        .withCountry("GB")
                        .withPostcode("GA12LN")
                        .build())
                .build();
        cmd.setCreateAccountDto(createdAccountDto);
        return cmd;
    }

    private AccountOperationCommand createMockAccountOperationCommand(TransactionType type, Double amount) {
        AccountOperationCommand cmd = new AccountOperationCommand();
        cmd.setTransactionType(type);
        cmd.setTransactionDto(new TransactionDto(amount, "account123"));
        return cmd;
    }
}

