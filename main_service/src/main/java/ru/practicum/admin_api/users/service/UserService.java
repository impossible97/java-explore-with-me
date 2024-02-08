package ru.practicum.admin_api.users.service;

import ru.practicum.admin_api.users.dto.NewUserDto;
import ru.practicum.admin_api.users.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto createUser(NewUserDto userDto);

    void deleteUser(long userId);
}
