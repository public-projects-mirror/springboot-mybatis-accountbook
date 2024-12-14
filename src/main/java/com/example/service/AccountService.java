package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.AccountDTO;
import com.example.entity.AccountDO;
import com.example.exception.AccountNotFoundException;
import com.example.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService extends ServiceImpl<AccountMapper, AccountDO> {

    @Transactional
    public AccountDTO saveAccount(BigDecimal amount, LocalDate date, String category, String type, String remarks) {
        AccountDO newAccount = new AccountDO();
        newAccount.setAccountId(UUID.randomUUID().toString());
        newAccount.setAmount(amount);
        newAccount.setDate(date);
        newAccount.setCategory(category);
        newAccount.setType(type);
        newAccount.setRemarks(remarks);
        this.save(newAccount);
        return AccountDTO.newFromDO().apply(newAccount);
    }

    @Transactional
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
        accountDO.setAmount(amount);
        accountDO.setCategory(category);
        accountDO.setType(type);
        accountDO.setRemarks(remarks);
        this.updateById(accountDO);
        return AccountDTO.newFromDO().apply(accountDO);
    }

    @Transactional
    public void deleteAccount(String accountId) {
        AccountDO accountDO = this.getById(accountId);
        if (accountDO == null) {
            throw new AccountNotFoundException("account not found");
        }
        this.removeById(accountDO);
    }

    // 通用汇总函数
    public BigDecimal getTotalAmount(String type, String month, String categoryName) {
        QueryWrapper<AccountDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);  // 收入或支出

        // 按月份汇总
        if (month != null && !month.isEmpty()) {
            queryWrapper.like("date", month);  // 按月份筛选
        }

        // 按类别汇总
        if (categoryName != null && !categoryName.isEmpty()) {
            queryWrapper.eq("category", categoryName);  // 按类别筛选
        }

        // 查询并汇总金额
        List<AccountDO> bills = this.baseMapper.selectList(queryWrapper);
        return bills.stream().map(AccountDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 获取总收入
    public BigDecimal getTotalIncome() {
        return getTotalAmount("income", null, null);  // 不指定月份和类别
    }

    // 获取总支出
    public BigDecimal getTotalExpense() {
        return getTotalAmount("outcome", null, null);  // 不指定月份和类别
    }

    // 按月汇总收入
    public BigDecimal getIncomeByMonth(String month) {
        return getTotalAmount("income", month, null);  // 指定月份
    }

    // 按类别汇总收入
    public BigDecimal getIncomeByCategory(String categoryId) {
        return getTotalAmount("income", null, categoryId);  // 指定类别
    }

    // 按月汇总支出
    public BigDecimal getExpenseByMonth(String month) {
        return getTotalAmount("outcome", month, null);  // 指定月份
    }

    // 按类别汇总支出
    public BigDecimal getExpenseByCategory(String categoryId) {
        return getTotalAmount("outcome", null, categoryId);  // 指定类别
    }

}