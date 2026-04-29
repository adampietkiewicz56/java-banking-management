package com.pietkiewicz.bankapp.controller;

import com.pietkiewicz.bankapp.dto.AmountRequestDTO;
import com.pietkiewicz.bankapp.dto.CreateAccountRequestDTO;
import com.pietkiewicz.bankapp.dto.TransferRequestDTO;
import com.pietkiewicz.bankapp.entity.Account;
import com.pietkiewicz.bankapp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request.getUserId()));
    }

    @GetMapping
    public ResponseEntity<Page<Account>> getAll(Pageable pageable) {
        return ResponseEntity.ok(
                accountService.getAllAccounts(pageable)
        );
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id,
                                           @RequestBody AmountRequestDTO request) {
        return ResponseEntity.ok(
                accountService.deposit(id, request.getAmount())
        );
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id,
                                            @RequestBody AmountRequestDTO request) {
        return ResponseEntity.ok(
                accountService.withdraw(id, request.getAmount())
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO request) {
        accountService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        return ResponseEntity.ok("Transfer completed");
    }
}