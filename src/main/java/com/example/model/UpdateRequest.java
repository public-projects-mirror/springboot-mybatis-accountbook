package com.example.model;

public class UpdateRequest {
    private DeleteAccountRequest deleteAccountRequest;
    private UpdateAccountRequest updateAccountRequest;

    public DeleteAccountRequest getAccountIdRequest() {
        return deleteAccountRequest;
    }

    public UpdateAccountRequest getUpdateAccountRequest() {
        return updateAccountRequest;
    }
}
