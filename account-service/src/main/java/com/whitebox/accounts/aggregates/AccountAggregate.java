package com.whitebox.accounts.aggregates;

import com.whitebox.accounts.commands.CloseAccountCommand;
import com.whitebox.accounts.commands.CreateAccountCommand;
import com.whitebox.accounts.commands.DepositAmountCommand;
import com.whitebox.accounts.commands.WithDrawAmountCommand;
import com.whitebox.accounts.handlers.events.CloseEvent;
import com.whitebox.accounts.handlers.events.CreateAccountEvent;
import com.whitebox.accounts.handlers.events.DepositEvent;
import com.whitebox.accounts.handlers.events.WithdrawEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand accountCommand) {
        CreateAccountEvent createAccountEvent = CreateAccountEvent.builder().id(accountCommand.getId()).accountType(accountCommand.getAccountType()).accountHolderId(accountCommand.getAccountHolderId()).creationDate(new Date()).openingBalance(accountCommand.getOpeningBalance()).build();
        AggregateLifecycle.apply(createAccountEvent);
    }

    @EventSourcingHandler
    public void on(CreateAccountEvent createAccountEvent) {
        this.id = createAccountEvent.getId();
        this.accountHolderId = createAccountEvent.getAccountHolderId();
        this.balance = createAccountEvent.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositAmountCommand depositAmountCommand) {
        double amount = depositAmountCommand.getAmount();
        DepositEvent depositEvent = DepositEvent.builder().id(depositAmountCommand.getId()).amount(depositAmountCommand.getAmount()).currentBalance(amount + this.balance).build();
        AggregateLifecycle.apply(depositEvent);
    }

    @EventSourcingHandler
    public void on(DepositEvent depositEvent) {
        this.balance += depositEvent.getAmount();
    }

    @CommandHandler
    public void handle(WithDrawAmountCommand withDrawAmountCommand) {
        double amount = withDrawAmountCommand.getAmount();
        if(this.balance - amount < 0) {
            throw new IllegalStateException("Withdraw declined, insufficient funds");
        }

        WithdrawEvent withdrawEvent = WithdrawEvent.builder().id(withDrawAmountCommand.getId()).amount(amount).currentBalance(this.balance - amount).build();
        AggregateLifecycle.apply(withdrawEvent);
    }

    @EventSourcingHandler
    public void on(WithdrawEvent withdrawEvent) {
        this.balance -= withdrawEvent.getAmount();
    }

    @CommandHandler
    public void handle(CloseAccountCommand closeAccountCommand){
        CloseEvent closeEvent = CloseEvent.builder().id(closeAccountCommand.getId()).build();
        AggregateLifecycle.apply(closeEvent);
    }

    @EventSourcingHandler
    public void on(CloseEvent closeEvent) {
        AggregateLifecycle.markDeleted();
    }

}
