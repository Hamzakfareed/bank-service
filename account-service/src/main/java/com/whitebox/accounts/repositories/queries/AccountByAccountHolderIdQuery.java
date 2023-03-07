package com.whitebox.accounts.repositories.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountByAccountHolderIdQuery {
    private String accountHolderId;
}
