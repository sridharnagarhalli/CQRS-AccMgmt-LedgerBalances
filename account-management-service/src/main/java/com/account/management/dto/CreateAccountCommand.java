package com.account.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAccountCommand {
    @NonNull
    private CreateAccountDto createAccountDto;
}
