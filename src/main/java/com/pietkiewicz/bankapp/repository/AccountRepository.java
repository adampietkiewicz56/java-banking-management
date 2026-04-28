package com.pietkiewicz.bankapp.repository;

import com.pietkiewicz.bankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}