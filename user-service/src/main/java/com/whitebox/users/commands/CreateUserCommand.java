package com.whitebox.users.commands;

import com.whitebox.users.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private User user;
}
