package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.AccountDTO;
import com.example.entity.AccountDO;
import com.example.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService extends ServiceImpl<AccountMapper, AccountDO> {
    public AccountDTO save(BigDecimal amount, String category, String type, String remarks) {
        AccountDO newAccount = new AccountDO();
        newAccount.setAccountId(UUID.randomUUID().toString());
        return updateAccount(newAccount, amount, category, type, remarks);
    }

    public List<AccountDTO> listAll() {
        return lambdaQuery()
                .list()
                .stream()
                .map(AccountDTO.newFromDO())
                .collect(Collectors.toList());
    }

    public AccountDTO get(String accountId) {
        AccountDO accountDO = this.getById(accountId);
        if (accountDO == null) {
            return null;
        }
        return AccountDTO.newFromDO().apply(accountDO);
    }

    public AccountDTO update(String accountId, BigDecimal amount, String category, String type, String remarks) {
        AccountDO accountDO = this.getById(accountId);
        assert accountDO != null;
        return updateAccount(accountDO, amount, category, type, remarks);
    }

    public void delete(String accountId) {
        this.removeById(accountId);
    }

    public AccountDTO updateAccount(AccountDO accountDO, BigDecimal amount, String category, String type, String remarks){
        accountDO.setAmount(amount);
        accountDO.setDate(LocalDate.now());
        accountDO.setCategory(category);
        accountDO.setType(type);
        accountDO.setRemarks(remarks);
        this.save(accountDO);
        return AccountDTO.newFromDO().apply(accountDO);
    }
}