package com.example.controller;

import com.example.dto.AccountDTO;
import com.example.model.*;
import com.example.response.ApiResponse;
import com.example.service.AccountService;
import com.example.service.CategoryService;
import com.example.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
public class

AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ApiResponse<AccountVO> addAccount(@RequestBody AddAccountRequest addAccountRequest) {
        BigDecimal amount = addAccountRequest.getAmount();
        LocalDate date = addAccountRequest.getDate();
        String category = addAccountRequest.getCategory();
        String type = addAccountRequest.getType();
        String remarks = addAccountRequest.getRemarks();
        AccountDTO accountDTO = accountService.saveAccount(amount, date, category, type, remarks);
        if (!categoryService.CategoryExist(category)) {
            categoryService.saveCategory(category);
        }
        ApiResponse<AccountVO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(new AccountVO(accountDTO));
        apiResponse.setMessage("save success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ApiResponse<List<AccountVO>> getAllAccounts() {
        List<AccountVO> accountVOList = accountService.listAllAccounts()
                .stream()
                .map(AccountVO::new)
                .toList();
        ApiResponse<List<AccountVO>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(accountVOList);
        apiResponse.setMessage("list success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get")
    public ApiResponse<AccountVO> getAccountById(@RequestBody GetAccountRequest getAccountRequest) {
        String accountId = getAccountRequest.getAccountId();
        AccountDTO accountDTO = accountService.getAccount(accountId);
        ApiResponse<AccountVO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(new AccountVO(accountDTO));
        apiResponse.setMessage("get success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ApiResponse<AccountVO> updateAccountById(@RequestBody UpdateRequest updateRequest) {
        UpdateAccountRequest updateAccountRequest = updateRequest.getUpdateAccountRequest();
        String accountId = updateRequest.getAccountId();
        BigDecimal amount = updateAccountRequest.getAmount();
        String category = updateAccountRequest.getCategory();
        String type = updateAccountRequest.getType();
        String remarks = updateAccountRequest.getRemarks();
        if (!categoryService.CategoryExist(category)) {
            categoryService.saveCategory(category);
        }
        AccountDTO accountDTO = accountService.updateAccount(accountId, amount, category, type, remarks);
        ApiResponse<AccountVO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(new AccountVO(accountDTO));
        apiResponse.setMessage("update success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ApiResponse<String> deleteAccountById(@RequestBody DeleteAccountRequest deleteAccountRequest) {
        String accountId = deleteAccountRequest.getAccountId();
        accountService.deleteAccount(accountId);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("delete success");
        return apiResponse;
    }

    // 获取总收入
    @RequestMapping(method = RequestMethod.GET, value = "/total-income")
    public BigDecimal getTotalIncome() {
        return accountService.getTotalIncome();
    }

    // 获取总支出
    @RequestMapping(method = RequestMethod.GET, value ="/total-expense")
    public BigDecimal getTotalExpense() {
        return accountService.getTotalExpense();
    }

    // 按月汇总收入
    @RequestMapping(method = RequestMethod.GET, value ="/income-by-month")
    public BigDecimal getIncomeByMonth(@RequestParam String month) {
        return accountService.getIncomeByMonth(month);
    }

    // 按类别汇总收入
    @RequestMapping(method = RequestMethod.GET, value ="/income-by-category")
    public BigDecimal getIncomeByCategory(@RequestParam String categoryName) {
        return accountService.getIncomeByCategory(categoryName);
    }

    // 按月汇总支出
    @RequestMapping(method = RequestMethod.GET, value ="/expense-by-month")
    public BigDecimal getExpenseByMonth(@RequestParam String month) {
        return accountService.getExpenseByMonth(month);
    }

    // 按类别汇总支出
    @RequestMapping(method = RequestMethod.GET, value ="/expense-by-category")
    public BigDecimal getExpenseByCategory(@RequestParam String categoryName) {
        return accountService.getExpenseByCategory(categoryName);
    }

    // 自定义汇总支出
    @RequestMapping(method = RequestMethod.GET, value = "/custom-input")
    public BigDecimal getCustomInput(@RequestParam String type,@RequestParam String month,
                                     @RequestParam String categoryName) {
        return accountService.getTotalAmount(type, month, categoryName);
    }

}
