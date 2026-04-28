package com.pietkiewicz.bankapp.controller;

import com.pietkiewicz.bankapp.dto.CreateAccountRequest;
import com.pietkiewicz.bankapp.entity.Account;
import com.pietkiewicz.bankapp.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request.getUserId());
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}