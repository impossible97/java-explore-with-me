package ru.practicum.admin_api.users.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.admin_api.users.dto.UserShortDto;
import ru.practicum.admin_api.users.model.User;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByIdInOrderById(List<Long> ids, Pageable pageable);

    UserShortDto findUserById(long id);
}
