package com.pietkiewicz.bankapp.service;

import com.pietkiewicz.bankapp.entity.Account;
import com.pietkiewicz.bankapp.entity.User;
import com.pietkiewicz.bankapp.repository.AccountRepository;
import com.pietkiewicz.bankapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountNumber(generateNumber());

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    private String generateNumber() {
        return "ACC" + (100000 + new Random().nextInt(900000));
    }


    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow();

        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow();

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}