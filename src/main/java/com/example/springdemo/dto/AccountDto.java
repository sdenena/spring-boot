package com.example.springdemo.dto;

import com.example.springdemo.models.Account;
import com.example.springdemo.models.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id = null;

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName = null;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    private BigDecimal balance = BigDecimal.ZERO;

    public Account toAccount() {
        return new Account(id, accountHolderName, accountType, balance);
    }

    public Account updateAccount(Account account) {
        account.setAccountHolderName(accountHolderName);
        account.setAccountType(accountType);
        account.setBalance(balance);
        return account;
    }

    // Static factory method for creating DTO from Account entity
    public static AccountDto fromAccount(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getAccountType(),
                account.getBalance()
        );
    }
}
