package com.whitebox.users.handlers.events;

import com.whitebox.users.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserEvent {
    private String id;
    private User user;
}
