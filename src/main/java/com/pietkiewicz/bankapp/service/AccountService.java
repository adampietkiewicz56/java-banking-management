package com.pietkiewicz.bankapp.service;

import com.pietkiewicz.bankapp.entity.Account;
import com.pietkiewicz.bankapp.entity.User;
import com.pietkiewicz.bankapp.repository.AccountRepository;
import com.pietkiewicz.bankapp.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.pietkiewicz.bankapp.entity.Transaction;
import com.pietkiewicz.bankapp.entity.TransactionType;
import java.time.LocalDateTime;
import com.pietkiewicz.bankapp.exception.BadRequestException;
import com.pietkiewicz.bankapp.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository,
                          TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
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
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        Account savedAccount = accountRepository.save(account);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(amount);
        tx.setCreatedAt(LocalDateTime.now());
        tx.setToAccountId(accountId);
        transactionService.save(tx);

        return savedAccount;
    }

    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account savedAccount = accountRepository.save(account);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.WITHDRAW);
        tx.setAmount(amount);
        tx.setCreatedAt(LocalDateTime.now());
        tx.setFromAccountId(accountId);
        transactionService.save(tx);

        return savedAccount;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = accountRepository.findById(fromId).orElseThrow(() -> new NotFoundException("Account not found"));
        Account to = accountRepository.findById(toId).orElseThrow(() -> new NotFoundException("Account not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.TRANSFER);
        tx.setAmount(amount);
        tx.setCreatedAt(LocalDateTime.now());
        tx.setFromAccountId(fromId);
        tx.setToAccountId(toId);
        transactionService.save(tx);
    }
}