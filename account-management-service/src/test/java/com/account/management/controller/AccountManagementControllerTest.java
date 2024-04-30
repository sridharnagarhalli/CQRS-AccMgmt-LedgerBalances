package com.account.management.controller;

import com.account.management.dto.AccountDto;
import com.account.management.dto.AccountOperationCommand;
import com.account.management.dto.AddressDto;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.dto.CreateAccountDto;
import com.account.management.dto.TransactionDto;
import com.account.management.dto.TransactionType;
import com.account.management.exception.AccountManagementException;
import com.account.management.service.AccountManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AccountManagementControllerTest {

    @Mock
    private AccountManagementService accountManagementService;

    @InjectMocks
    private AccountManagementController accountManagementController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount_Exception() {
        var createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setCreateAccountDto(CreateAccountDto.builder()
                        .withFirstName("John")
                        .withLastName("Doe")
                        .withNiNumber("12345")
                        .withDepositInitialAmount(-100.0)
                        .withAddressDto(AddressDto.builder()
                                .withAddressLine1("123 Main St")
                                .withCounty("GB")
                                .withCountry("GB")
                                .withPostcode("GA12LN")
                                .build())
                        .build());

        var exception = assertThrows(AccountManagementException.class,
                () -> accountManagementController.createAccount(createAccountCommand));
        assertTrue(exception.getMessage().contains("Deposit amount should be greater than zero."));
    }

    @Test
    public void testCreateAccountSuccessfull() {
        var createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setCreateAccountDto(CreateAccountDto.builder()
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
                .build());

        var createdAccountDto = AccountDto.builder()
                .withAccountNumber("1234567890")
                .withFirstName("John")
                .withLastName("Doe")
                .withBalance(100.0)
                .withAccountCreatedTime(Instant.now())
                .withLastTransactionTime(Instant.now())
                .withAddressDto(AddressDto.builder()
                        .withAddressLine1("123 Main St")
                        .withCounty("GB")
                        .withCountry("GB")
                        .withPostcode("GA12LN")
                        .build())
                .build();

        when(accountManagementService.createAccount(createAccountCommand)).thenReturn(createdAccountDto);

        var response = accountManagementController.createAccount(createAccountCommand);
        assertEquals(createdAccountDto, response);
    }

    @Test
    public void testTransactSuccessful() {
        var accountOperationCommand = new AccountOperationCommand();
        accountOperationCommand.setTransactionType(TransactionType.DEPOSIT);
        accountOperationCommand.setTransactionDto(new TransactionDto(100.00, "account123"));
        var accountDto = AccountDto.builder()
                .withAccountNumber("account123")
                .withBalance(100.0)
                .withAddressDto(AddressDto.builder()
                        .withAddressLine1("123 Main St")
                        .withCounty("GB")
                        .withCountry("GB")
                        .withPostcode("GA12LN")
                        .build())
                .build();
        when(accountManagementService.transact(accountOperationCommand)).thenReturn(accountDto);
        var response = accountManagementController.transact(accountOperationCommand);
        assertEquals(accountDto.getAccountNumber(), response.getAccountNumber());
        assertEquals(accountDto.getBalance(), response.getBalance());
    }
}
