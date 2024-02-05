package ru.practicum.private_api.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.event.model.Event;

import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findAllByInitiatorId(long userId, Pageable pageable, Sort sort);

    Event findByIdAndInitiatorId(long eventId, long userId);
}
