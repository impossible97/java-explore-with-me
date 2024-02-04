package ru.practicum.admin_api.user.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.user.dto.UserDto;
import ru.practicum.admin_api.user.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        return userRepository.findAllByIdInOrderById(ids, PageRequest.of(from / size, size))
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id= " + userId + " не найден")));
    }
}
