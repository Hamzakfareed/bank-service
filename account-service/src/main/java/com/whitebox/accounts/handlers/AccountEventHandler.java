package com.whitebox.accounts.handlers;

import com.whitebox.accounts.handlers.events.CloseEvent;
import com.whitebox.accounts.handlers.events.CreateAccountEvent;
import com.whitebox.accounts.handlers.events.DepositEvent;
import com.whitebox.accounts.handlers.events.WithdrawEvent;

public interface AccountEventHandler {
    void on(CreateAccountEvent createAccountEvent);
    void on(DepositEvent depositEvent);
    void on(WithdrawEvent withdrawEvent);
    void on(CloseEvent closeEvent);
}
