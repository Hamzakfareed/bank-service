package com.whitebox.users.aggregates;

import com.whitebox.users.commands.CreateUserCommand;
import com.whitebox.users.commands.DeleteUserCommand;
import com.whitebox.users.commands.UpdateUserCommand;
import com.whitebox.users.handlers.events.CreateUserEvent;
import com.whitebox.users.handlers.events.DeleteUserEvent;
import com.whitebox.users.handlers.events.UpdateUserEvent;
import com.whitebox.users.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private User user;

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        User user = createUserCommand.getUser();
        user.setId(createUserCommand.getId());
        String password = user.getAccount().getPassword();
        user.getAccount().setPassword(password);
        CreateUserEvent event = CreateUserEvent.builder().id(createUserCommand.getId()).user(user).build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {
        User user = updateUserCommand.getUser();
        user.setId(updateUserCommand.getId());
        String password = user.getAccount().getPassword();
        user.getAccount().setPassword(password);
        UpdateUserEvent event = UpdateUserEvent.builder().id(UUID.randomUUID().toString()).user(user).build();
        AggregateLifecycle.apply(event);

    }

    @CommandHandler
    public void handle(DeleteUserCommand deleteUserCommand) {
        DeleteUserEvent event = new DeleteUserEvent();
        event.setId(deleteUserCommand.getId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CreateUserEvent createUserEvent) {
        this.id = createUserEvent.getId();
        this.user = createUserEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UpdateUserEvent updateUserEvent) {
        this.user = updateUserEvent.getUser();
    }

    @EventSourcingHandler
    public void on(DeleteUserEvent deleteUserEvent) {
        AggregateLifecycle.markDeleted();
    }
}
