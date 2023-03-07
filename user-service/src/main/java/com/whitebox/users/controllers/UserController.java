package com.whitebox.users.controllers;

import com.whitebox.users.commands.CreateUserCommand;
import com.whitebox.users.commands.DeleteUserCommand;
import com.whitebox.users.commands.UpdateUserCommand;
import com.whitebox.users.dto.UserDto;
import com.whitebox.users.repositories.queries.FindAllUsersQuery;
import com.whitebox.users.repositories.queries.FindUserByIdQuery;
import com.whitebox.users.repositories.queries.FindUsersQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public UserController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserCommand createUserCommand) {
        try {
            createUserCommand.setId(UUID.randomUUID().toString());
            commandGateway.sendAndWait(createUserCommand);
            return ControllerHelper.prepareResponse("User successfully created");
        } catch (Exception e) {
            e.printStackTrace();
            return ControllerHelper.prepareErrorResponse("An error occurred while creating user with id : "+ createUserCommand.getId());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable(name = "id") String userId, @RequestBody UpdateUserCommand updateUserCommand) {
        try {
            updateUserCommand.setId(userId);
            commandGateway.send(updateUserCommand);
            return ControllerHelper.prepareResponse("User successfully updated");
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while updating user with id : "+ updateUserCommand.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(name = "id") String userId) {
        try {
            commandGateway.send(new DeleteUserCommand(userId));
            return ControllerHelper.prepareResponse("User successfully deleted");
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while delete user with id : "+ userId);
        }
    }

    @GetMapping
    public ResponseEntity findAllUsers() {
        try {
            var query = new FindAllUsersQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserDto.class)).join();
            return ControllerHelper.prepareResponse(response);
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while retrieving users");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findUserById(@PathVariable(name = "id") String userId) {
        try {
            var query = new FindUserByIdQuery(userId);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserDto.class)).join();
            return ControllerHelper.prepareResponse(response);
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while retrieving users");
        }
    }

    @GetMapping("/{filter}")
    public ResponseEntity findUserByFilter(@PathVariable(name = "filter") String filter) {
        try {
            var query = new FindUsersQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserDto.class)).join();
            return ControllerHelper.prepareResponse(response);
        } catch (Exception e) {
            return ControllerHelper.prepareErrorResponse("An error occurred while retrieving users");
        }
    }
}
