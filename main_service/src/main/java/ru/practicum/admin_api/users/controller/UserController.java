package ru.practicum.admin_api.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.users.dto.NewUserDto;
import ru.practicum.admin_api.users.dto.UserDto;
import ru.practicum.admin_api.users.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("admin/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                           @RequestParam(name = "from", defaultValue = "0") int from,
                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return userService.getUsers(ids, from, size);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    UserDto createUser(@Validated @RequestBody NewUserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
