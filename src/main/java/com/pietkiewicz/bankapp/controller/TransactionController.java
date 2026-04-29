package com.pietkiewicz.bankapp.controller;

import com.pietkiewicz.bankapp.entity.Transaction;
import com.pietkiewicz.bankapp.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Page<Transaction>> getAll(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAll(pageable));
    }
}