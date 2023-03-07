package com.whitebox.accounts.handlers;

import com.whitebox.accounts.dto.AccountDto;
import com.whitebox.accounts.models.BankAccount;
import com.whitebox.accounts.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import com.whitebox.accounts.repositories.queries.AccountByAccountHolderIdQuery;
import com.whitebox.accounts.repositories.queries.AccountByIdQuery;
import com.whitebox.accounts.repositories.queries.AccountsQuery;

import java.util.Optional;

@Service
public class AccountQueryHanderImpl implements AccountQueryHandler {

    private AccountRepository accountRepository;

    public AccountQueryHanderImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountDto findAccountById(AccountByIdQuery query) {
        Optional<BankAccount> account = accountRepository.findById(query.getId());
        return new AccountDto(account.get());
    }

    @QueryHandler
    @Override
    public AccountDto findAllAccounts(AccountsQuery accountsQuery) {
        return new AccountDto(accountRepository.findAll());
    }

    @QueryHandler
    @Override
    public AccountDto findAccountByAccountHolderId(AccountByAccountHolderIdQuery accountByAccountHolderIdQuery) {
        Optional<BankAccount> account = accountRepository.findByAccountHolderId(accountByAccountHolderIdQuery.getAccountHolderId());
        return new AccountDto(account.get());
    }
}
