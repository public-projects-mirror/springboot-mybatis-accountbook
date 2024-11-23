package com.example.controller;

import com.example.dto.AccountDTO;
import com.example.model.AccountIdRequest;
import com.example.model.AddAccountRequest;
import com.example.model.UpdateAccountRequest;
import com.example.model.UpdateRequest;
import com.example.response.ApiResponse;
import com.example.service.AccountService;
import com.example.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ApiResponse<AccountVO> addAccount(@RequestBody AddAccountRequest addAccountRequest) {
        BigDecimal amount = addAccountRequest.getAmount();
        String category = addAccountRequest.getCategory();
        String type = addAccountRequest.getType();
        String remarks = addAccountRequest.getRemarks();
        AccountDTO accountDTO = accountService.save(amount, category, type, remarks);
        ApiResponse<AccountVO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(new AccountVO(accountDTO));
        apiResponse.setMessage("save success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ApiResponse<List<AccountVO>> getAllAccounts() {
        List<AccountVO> accountVOList = accountService.listAll()
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
    public ApiResponse<AccountVO> getAccountById(@RequestBody AccountIdRequest accountIdRequest) {
        String accountId = accountIdRequest.getAccountId();
        AccountDTO accountDTO = accountService.get(accountId);
        ApiResponse<AccountVO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(new AccountVO(accountDTO));
        apiResponse.setMessage("get success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ApiResponse<AccountVO> updateAccount(@RequestBody UpdateRequest updateRequest) {
        AccountIdRequest accountIdRequest = updateRequest.getAccountIdRequest();
        UpdateAccountRequest updateAccountRequest = updateRequest.getUpdateAccountRequest();
        String accountId = accountIdRequest.getAccountId();
        BigDecimal amount = updateAccountRequest.getAmount();
        String category = updateAccountRequest.getCategory();
        String type = updateAccountRequest.getType();
        String remarks = updateAccountRequest.getRemarks();
        AccountDTO accountDTO = accountService.update(accountId, amount, category, type, remarks);
        ApiResponse<AccountVO> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(new AccountVO(accountDTO));
        apiResponse.setMessage("update success");
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ApiResponse<String> deleteAccount(@RequestBody AccountIdRequest accountIdRequest) {
        String accountId = accountIdRequest.getAccountId();
        accountService.delete(accountId);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("delete success");
        return apiResponse;
    }

}
