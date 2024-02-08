package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.users.dto.NewUserDto;
import ru.practicum.admin_api.users.dto.UserDto;
import ru.practicum.admin_api.users.model.User;

@Component
public class UserMapper {

    public User toEntity(NewUserDto userDto) {
        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
