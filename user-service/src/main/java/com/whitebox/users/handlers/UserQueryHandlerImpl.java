package com.whitebox.users.handlers;

import com.whitebox.users.dto.UserDto;
import com.whitebox.users.models.User;
import com.whitebox.users.repositories.queries.FindAllUsersQuery;
import com.whitebox.users.repositories.queries.FindUserByIdQuery;
import com.whitebox.users.repositories.queries.FindUsersQuery;
import com.whitebox.users.repositories.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;

    public UserQueryHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    @Override
    public UserDto findUserById(FindUserByIdQuery findUserByIdQuery) {
        User user = userRepository.findById(findUserByIdQuery.getId()).get();
        return new UserDto(user);
    }

    @QueryHandler
    @Override
    public UserDto findUsers(FindUsersQuery findUsersQuery) {
        return new UserDto(userRepository.findAll());
    }

    @QueryHandler
    @Override
    public UserDto findAllUsers(FindAllUsersQuery findAllUsersQuery) {
        return new UserDto(userRepository.findAll());
    }
}
