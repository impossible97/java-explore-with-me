package ru.practicum.private_api.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findAllByInitiatorId(long userId, Pageable pageable);

    List<Event> findAllByIdIn(Set<Long> ids);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
            List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, Pageable pageable);

    List<Event> findAllByDescriptionIsLikeIgnoreCaseOrAnnotationIsLikeIgnoreCaseAndCategoryIdInAndPaidAndEventDateAfterAndEventDateBeforeAndState(
            String textForDescription, String textForAnnotation, List<Long> categories, boolean paid, LocalDateTime start,
            LocalDateTime end, EventState state, Pageable pageable
    );

    List<Event> findAllByDescriptionIsLikeIgnoreCaseOrAnnotationIsLikeIgnoreCaseAndCategoryIdInAndPaidAndEventDateAfterAndState(
            String textForDescription, String textForAnnotation, List<Long> categories, boolean paid, LocalDateTime now,
            EventState state, Pageable pageable
    );

    List<Event> findAllByDescriptionIsLikeIgnoreCaseOrAnnotationIsLikeIgnoreCaseAndCategoryIdInAndState(
            String textForDescription, String textForAnnotation, List<Long> categories, EventState state, Pageable pageable
    );

    List<Event> findAllByCategoryIdInAndState(List<Long> categories, EventState state, Pageable pageable);

    Optional<Event> findByIdAndState(long id, EventState state);

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryIdIn(
            List<Long> users, List<EventState> states, List<Long> categories, Pageable pageable);

    List<Event> findAllByInitiatorIdInAndStateIn(List<Long> users, List<EventState> states, Pageable pageable);

    List<Event> findAllByInitiatorIdIn(List<Long> users, Pageable pageable);
}
