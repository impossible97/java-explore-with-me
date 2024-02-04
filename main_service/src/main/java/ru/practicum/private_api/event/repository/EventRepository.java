package ru.practicum.private_api.event.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.event.model.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
}
