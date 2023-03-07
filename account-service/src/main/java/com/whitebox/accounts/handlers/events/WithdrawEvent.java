package com.whitebox.accounts.handlers.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawEvent {
    private String id;
    private double amount;
    private double currentBalance;
}
