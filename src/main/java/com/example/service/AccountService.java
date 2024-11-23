package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.AccountDTO;
import com.example.entity.AccountDO;
import com.example.exception.AccountNotFoundException;
import com.example.mapper.AccountMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService extends ServiceImpl<AccountMapper, AccountDO> {

    public AccountDTO saveAccount(BigDecimal amount, String category, String type, String remarks) {
        AccountDO newAccount = new AccountDO();
        newAccount.setAccountId(UUID.randomUUID().toString());
        return switchAccount(newAccount, amount, category, type, remarks);
    }


    public List<AccountDTO> listAllAccounts() {
        return lambdaQuery()
                .list()
                .stream()
                .map(AccountDTO.newFromDO())
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO getAccount(String accountId) {
        AccountDO accountDO = this.getById(accountId);
        if (accountDO == null) {
            throw new AccountNotFoundException("account not found");
        }
        return AccountDTO.newFromDO().apply(accountDO);
    }

    @Transactional
    public AccountDTO updateAccount(String accountId, BigDecimal amount, String category, String type, String remarks) {
        AccountDO accountDO = this.getById(accountId);
        if (accountDO == null) {
            throw new AccountNotFoundException("account not found");
        }
        return switchAccount(accountDO, amount, category, type, remarks);
    }

    @Transactional
    public void deleteAccount(String accountId) {
        AccountDO accountDO = this.getById(accountId);
        if (accountDO == null) {
            throw new AccountNotFoundException("account not found");
        }
        this.removeById(accountDO);
    }

    public AccountDTO switchAccount(@NotNull AccountDO accountDO, BigDecimal amount, String category, String type, String remarks){
        accountDO.setAmount(amount);
        accountDO.setDate(LocalDate.now());
        accountDO.setCategory(category);
        accountDO.setType(type);
        accountDO.setRemarks(remarks);
        this.updateById(accountDO);
        return AccountDTO.newFromDO().apply(accountDO);
    }
}