package com.account.management.controller;

import com.account.management.dto.AccountDto;
import com.account.management.dto.AccountOperationCommand;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.exception.AccountManagementException;
import com.account.management.service.AccountManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/accounts-management")
@AllArgsConstructor
@RestController
public class AccountManagementController {

    private final AccountManagementService accountManagementService;

    @PostMapping(path = "/create")
    public AccountDto createAccount(@RequestBody CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getCreateAccountDto().getDepositInitialAmount() <= 0) {
            throw new AccountManagementException("Deposit amount should be greater than zero.");
        }
        return accountManagementService.createAccount(createAccountCommand);
    }

    @PostMapping("/transact")
    public AccountDto transact(@RequestBody AccountOperationCommand accountOperationCommand) {
        return accountManagementService.transact(accountOperationCommand);
    }
}
