package com.example.springdemo.controllers;

import com.example.springdemo.aop.AuditFilter;
import com.example.springdemo.base.response.ResponseMessage;
import com.example.springdemo.base.response.ResponseObj;
import com.example.springdemo.base.response.ResponsePage;
import com.example.springdemo.dto.AccountDto;
import com.example.springdemo.models.Account;
import com.example.springdemo.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseObj<Account> createAccount(@Valid @RequestBody AccountDto accountDto) {
        return new ResponseObj<>(accountService.createAccount(accountDto.toAccount()));
    }

    @GetMapping("/{id}")
    public ResponseObj<AccountDto> getAccountById(@PathVariable Long id) {
        return new ResponseObj<>(accountService.getAccountDetail(id).toAccountDto());
    }

    @PutMapping("/{id}")
    public ResponseObj<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto account) {
        return new ResponseObj<>(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseMessage deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseMessage();
    }

    @AuditFilter()
    @GetMapping("/list")
    public ResponsePage<AccountDto> getAccountListPage(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var listPage = accountService.getAccountList(query, page, size);
        var accountList = listPage.getContent().stream().map(Account::toAccountDto).toList();
        return new ResponsePage<>(accountList, listPage.getTotalElements());
    }
}
