package ru.practicum.private_api.request.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.request.model.Request;

import java.util.List;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

    boolean existsByEventIdAndRequesterId(long eventId, long userId);

    List<Request> findAllByRequesterId(long userId);

    List<Request> findAllByEventId(long eventId);

    List<Request> findAllByEventIdAndIdIn(long eventId, List<Long> ids);
}
