package com.whitebox.accounts.handlers;

import com.whitebox.accounts.handlers.events.CloseEvent;
import com.whitebox.accounts.handlers.events.CreateAccountEvent;
import com.whitebox.accounts.handlers.events.DepositEvent;
import com.whitebox.accounts.handlers.events.WithdrawEvent;
import com.whitebox.accounts.models.BankAccount;
import com.whitebox.accounts.repositories.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ProcessingGroup("bankacount-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(CreateAccountEvent createAccountEvent) {
        BankAccount bankAccount = BankAccount.builder().id(createAccountEvent.getId()).accountHolderId(createAccountEvent.getAccountHolderId()).creationDate(createAccountEvent.getCreationDate()).accountType(createAccountEvent.getAccountType()).balance(createAccountEvent.getOpeningBalance()).build();
        accountRepository.save(bankAccount);
    }

    @EventHandler
    @Override
    public void on(DepositEvent depositEvent) {
        Optional<BankAccount> account = accountRepository.findById(depositEvent.getId());
        if (account.isEmpty()) {
            return;
        }

        account.get().setBalance(depositEvent.getAmount());
        accountRepository.save(account.get());
    }

    @EventHandler
    @Override
    public void on(WithdrawEvent withdrawEvent) {
        Optional<BankAccount> account = accountRepository.findById(withdrawEvent.getId());
        if (account.isEmpty()) {
            return;
        }

        account.get().setBalance(withdrawEvent.getAmount());
        accountRepository.save(account.get());
    }

    @EventHandler
    @Override
    public void on(CloseEvent closeEvent) {
        accountRepository.deleteById(closeEvent.getId());
    }
}
