package ru.practicum.admin_api.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.user.service.UserService;
import ru.practicum.admin_api.user.dto.UserDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(params = "/admin/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    List<UserDto> getUsers(@RequestParam(name = "ids") List<Long> ids,
                           @RequestParam(name = "from", defaultValue = "0") int from,
                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return userService.getUsers(ids, from, size);
    }

    @PostMapping()
    UserDto createUser(@Validated @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("{userId}")
    void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
