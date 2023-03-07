package com.whitebox.users.dto;

import com.whitebox.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private List<User> users;

    public UserDto(User user) {
        users.add(user);
    }
}
