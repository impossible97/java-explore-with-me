package ru.practicum.admin_api.users.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_api.users.dto.UserDto;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        if (ids != null) {
            return userRepository.findAllByIdInOrderById(ids, PageRequest.of(from / size, size))
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAll(PageRequest.of(from / size, size))
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id= " + userId + " не найден")));
    }
}
