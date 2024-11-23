package com.example.dto;

import com.example.entity.AccountDO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

public class AccountDTO {
    private String accountId;
    private BigDecimal amount;  // 金额
    private LocalDate date;     // 时间
    private String category;  // 类别 (收入/支出)
    private String type;        // 类型 (工资、餐饮等)
    private String remarks;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static Function<AccountDO, AccountDTO> newFromDO() {
        return new Function<AccountDO, AccountDTO>() {
            @Override
            public AccountDTO apply(AccountDO accountDO) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountId(accountDO.getAccountId());
                accountDTO.setAmount(accountDO.getAmount());
                accountDTO.setDate(accountDO.getDate());
                accountDTO.setCategory(accountDO.getCategory());
                accountDTO.setType(accountDO.getType());
                accountDTO.setRemarks(accountDO.getRemarks());
                return accountDTO;
            }
        };
    }
}
