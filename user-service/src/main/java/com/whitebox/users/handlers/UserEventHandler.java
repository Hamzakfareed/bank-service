package com.whitebox.users.handlers;

import com.whitebox.users.handlers.events.CreateUserEvent;
import com.whitebox.users.handlers.events.DeleteUserEvent;
import com.whitebox.users.handlers.events.UpdateUserEvent;

public interface UserEventHandler {
    void on(CreateUserEvent createUserEvent);
    void on(UpdateUserEvent updateUserEvent);
    void on(DeleteUserEvent deleteUserEvent);
}
