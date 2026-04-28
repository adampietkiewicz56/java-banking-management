package com.pietkiewicz.bankapp.repository;

import com.pietkiewicz.bankapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}