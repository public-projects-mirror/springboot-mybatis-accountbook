package com.example.vo;

import com.example.dto.AccountDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountVO {
    private final transient AccountDTO accountDTO;

    public AccountVO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getId() {
        return accountDTO.getAccountId();
    }

    public BigDecimal getAmount() {
        return accountDTO.getAmount();
    }

    public LocalDate getDate() {
        return accountDTO.getDate();
    }

    public String getCategory() {
        return accountDTO.getCategory();
    }

    public String getType() {
        return accountDTO.getType();
    }

    public String getRemarks() {
        return accountDTO.getRemarks();
    }
}
