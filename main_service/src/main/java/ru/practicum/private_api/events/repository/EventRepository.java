package ru.practicum.private_api.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;

import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findAllByInitiatorId(long userId, Pageable pageable, Sort sort);

    List<Event> findAllByIdIn(List<Long> ids);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
            List<Long> users, List<EventState> states, List<Long> categories, String rangeStart, String rangeEnd, Pageable pageable);
}
