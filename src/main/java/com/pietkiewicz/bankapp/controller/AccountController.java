package com.pietkiewicz.bankapp.controller;

import com.pietkiewicz.bankapp.dto.AccountResponseDTO;
import com.pietkiewicz.bankapp.dto.AmountRequestDTO;
import com.pietkiewicz.bankapp.dto.CreateAccountRequestDTO;
import com.pietkiewicz.bankapp.dto.TransferRequestDTO;
import com.pietkiewicz.bankapp.entity.Account;
import com.pietkiewicz.bankapp.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private AccountResponseDTO map(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(
            @RequestBody CreateAccountRequestDTO request) {

        Account saved = accountService.createAccount(request.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(map(saved));
    }

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> getAll(Pageable pageable) {

        Page<AccountResponseDTO> result = accountService
                .getAllAccounts(pageable)
                .map(this::map);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(
            @PathVariable Long id,
            @RequestBody AmountRequestDTO request) {

        return ResponseEntity.ok(
                map(accountService.deposit(id, request.getAmount()))
        );
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponseDTO> withdraw(
            @PathVariable Long id,
            @RequestBody AmountRequestDTO request) {

        return ResponseEntity.ok(
                map(accountService.withdraw(id, request.getAmount()))
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestBody TransferRequestDTO request) {

        accountService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        return ResponseEntity.ok("Transfer completed");
    }
}