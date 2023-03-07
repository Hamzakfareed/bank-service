package com.whitebox.accounts.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class WithDrawAmountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
}
