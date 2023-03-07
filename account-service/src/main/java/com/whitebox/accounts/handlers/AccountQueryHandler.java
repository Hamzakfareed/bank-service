package com.whitebox.accounts.handlers;

import com.whitebox.accounts.dto.AccountDto;
import com.whitebox.accounts.repositories.queries.AccountByAccountHolderIdQuery;
import com.whitebox.accounts.repositories.queries.AccountByIdQuery;
import com.whitebox.accounts.repositories.queries.AccountsQuery;

public interface AccountQueryHandler {
    AccountDto findAccountById(AccountByIdQuery query);
    AccountDto findAllAccounts(AccountsQuery accountsQuery);
    AccountDto findAccountByAccountHolderId(AccountByAccountHolderIdQuery accountByAccountHolderIdQuery);
}
