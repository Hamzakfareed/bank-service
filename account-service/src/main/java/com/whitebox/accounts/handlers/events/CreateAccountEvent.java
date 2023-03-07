package com.whitebox.accounts.handlers.events;

import com.whitebox.accounts.models.AccountType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateAccountEvent {
    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private Date creationDate;
    private double openingBalance;
}
