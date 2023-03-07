package com.whitebox.accounts.dto;

import com.whitebox.accounts.models.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountDto {
    private List<BankAccount> accountList;

    public AccountDto(BankAccount bankAccount) {
        accountList.add(bankAccount);
    }
}
