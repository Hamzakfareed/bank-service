package com.whitebox.users.handlers;

import com.whitebox.users.handlers.events.CreateUserEvent;
import com.whitebox.users.handlers.events.DeleteUserEvent;
import com.whitebox.users.handlers.events.UpdateUserEvent;
import com.whitebox.users.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(CreateUserEvent createUserEvent) {
        userRepository.save(createUserEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(UpdateUserEvent updateUserEvent) {
        userRepository.save(updateUserEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(DeleteUserEvent deleteUserEvent) {
        userRepository.deleteById(deleteUserEvent.getId());
    }
}
