package com.pietkiewicz.bankapp.service;

import com.pietkiewicz.bankapp.entity.Account;
import com.pietkiewicz.bankapp.repository.AccountRepository;
import com.pietkiewicz.bankapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    @Test
    void depositShouldIncreaseBalance() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(account))
                .thenReturn(account);

        Account result = accountService.deposit(1L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), result.getBalance());
    }
    @Test
    void withdrawShouldDecreaseBalance() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(200));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(account))
                .thenReturn(account);

        Account result = accountService.withdraw(1L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), result.getBalance());
    }

    @Test
    void withdrawShouldThrowWhenInsufficientFunds() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(20));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(
                RuntimeException.class,
                () -> accountService.withdraw(1L, BigDecimal.valueOf(100))
        );
    }

    @Test
    void transferShouldMoveMoney() {
        Account from = new Account();
        from.setBalance(BigDecimal.valueOf(500));

        Account to = new Account();
        to.setBalance(BigDecimal.valueOf(100));

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(from));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        when(accountRepository.save(from)).thenReturn(from);
        when(accountRepository.save(to)).thenReturn(to);

        accountService.transfer(1L, 2L, BigDecimal.valueOf(200));

        assertEquals(BigDecimal.valueOf(300), from.getBalance());
        assertEquals(BigDecimal.valueOf(300), to.getBalance());
    }
}