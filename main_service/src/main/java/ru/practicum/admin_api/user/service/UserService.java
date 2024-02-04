package ru.practicum.admin_api.user.service;

import ru.practicum.admin_api.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto createUser(UserDto userDto);

    void deleteUser(long userId);
}
