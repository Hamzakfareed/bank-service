package com.whitebox.accounts.controllers;

import com.whitebox.accounts.commands.CloseAccountCommand;
import com.whitebox.accounts.commands.CreateAccountCommand;
import com.whitebox.accounts.commands.DepositAmountCommand;
import com.whitebox.accounts.commands.WithDrawAmountCommand;
import com.whitebox.accounts.dto.AccountDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.whitebox.accounts.repositories.queries.AccountByAccountHolderIdQuery;
import com.whitebox.accounts.repositories.queries.AccountByIdQuery;
import com.whitebox.accounts.repositories.queries.AccountsQuery;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private CommandGateway commandGateway;
    private QueryGateway queryGateway;

    public AccountController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody CreateAccountCommand createAccountCommand) {
        createAccountCommand.setId(UUID.randomUUID().toString());
        try {
            commandGateway.send(commandGateway);
            return ControllerHelper.prepareResponse("Successfully created new account");
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while creating account");
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity depositAmount(@PathVariable("accountId") String accountId, @RequestBody DepositAmountCommand depositAmountCommand) {
        try {
            depositAmountCommand.setId(accountId);
            commandGateway.send(depositAmountCommand);
            return ControllerHelper.prepareResponse("Amount successfully deposited");
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while depositing amount to account : " + accountId);
        }
    }

    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity withDraw(@PathVariable("accountId") String accountId, @RequestBody WithDrawAmountCommand withDrawAmountCommand) {
        try {
            withDrawAmountCommand.setId(accountId);
            commandGateway.send(withDrawAmountCommand).get();
            return ControllerHelper.prepareResponse("Amount successfully withdraw");
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while withdraw amount from account : " + accountId);
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity closeAccount(@PathVariable("accountId") String accountId) {
        try {
            CloseAccountCommand closeAccountCommand = CloseAccountCommand.builder().id(accountId).build();
            commandGateway.send(closeAccountCommand);
            return ControllerHelper.prepareResponse("Account successfully closed");
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while closing account : " + accountId);
        }
    }

    @GetMapping
    public ResponseEntity findAllAccounts() {
        try {
            AccountDto accountDto = queryGateway.query(new AccountsQuery(), ResponseTypes.instanceOf(AccountDto.class)).join();
            return ControllerHelper.prepareResponse(accountDto);
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while retrieving all bank acco unts" + e.getMessage());
        }
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity findAccountById(@PathVariable("accountId") String accountId) {
        try {
            AccountDto accountDto = queryGateway.query(new AccountByIdQuery(accountId), ResponseTypes.instanceOf(AccountDto.class)).join();
            return ControllerHelper.prepareResponse(accountDto);
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while retrieving account by id" + accountId);
        }
    }

    @RequestMapping(value = "/{accountHolderId}", method = RequestMethod.GET)
    public ResponseEntity findAccountByAccountHolderId(@PathVariable("accountHolderId") String accountHolderId) {
        try {
            AccountDto accountDto = queryGateway.query(new AccountByAccountHolderIdQuery(accountHolderId), ResponseTypes.instanceOf(AccountDto.class)).join();
            return ControllerHelper.prepareResponse(accountDto);
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while retrieving account by accountHolderId" + accountHolderId);
        }
    }
}
