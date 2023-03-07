package com.whitebox.users.handlers;

import com.whitebox.users.dto.UserDto;
import com.whitebox.users.repositories.queries.FindAllUsersQuery;
import com.whitebox.users.repositories.queries.FindUserByIdQuery;
import com.whitebox.users.repositories.queries.FindUsersQuery;

public interface UserQueryHandler {
    UserDto findUserById(FindUserByIdQuery findUserByIdQuery);
    UserDto findUsers(FindUsersQuery findUsersQuery);
    UserDto findAllUsers(FindAllUsersQuery findAllUsersQuery);
}
