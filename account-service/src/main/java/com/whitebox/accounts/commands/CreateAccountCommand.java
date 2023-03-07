package com.whitebox.accounts.commands;

import com.whitebox.accounts.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private double openingBalance;
}
