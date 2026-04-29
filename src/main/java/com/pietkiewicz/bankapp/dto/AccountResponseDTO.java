package com.pietkiewicz.bankapp.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountResponseDTO {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
}