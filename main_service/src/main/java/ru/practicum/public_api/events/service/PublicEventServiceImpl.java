package ru.practicum.public_api.events.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;
import ru.practicum.private_api.events.repository.EventRepository;
import ru.practicum.stats.Stats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;
    private final Stats stats;

    @Override
    public List<ShortEventDto> getEventsWithDescription(
            String text, List<Long> categories, boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
            String sort, int from, int size, HttpServletRequest request) {

        List<Event> events;
        Sort sortBy;

        if (sort.equals("EVENT_DATE")) {
            sortBy = Sort.by("eventDate").descending();
        } else {
            sortBy = Sort.by("views").descending();
        }

        if (rangeStart == null || rangeEnd == null) {
            events = eventRepository.findAllByDescriptionIsLikeIgnoreCaseOrAnnotationIsLikeIgnoreCaseAndCategoryIdInAndPaidAndEventDateAfterAndState(
                    text,
                    text,
                    categories,
                    paid,
                    LocalDateTime.now(),
                    EventState.PUBLISHED,
                    PageRequest.of(from / size, size, sortBy)
            );
        } else {
            events = eventRepository.findAllByDescriptionIsLikeIgnoreCaseOrAnnotationIsLikeIgnoreCaseAndCategoryIdInAndPaidAndEventDateAfterAndEventDateBeforeAndState(
                    text,
                    text,
                    categories,
                    paid,
                    rangeStart,
                    rangeEnd,
                    EventState.PUBLISHED,
                    PageRequest.of(from / size, size, sortBy)
            );
        }
        stats.hitStatistics(request);

        return events.stream()
                .filter(event -> !onlyAvailable || event.getConfirmedRequests() < event.getParticipantLimit())
                .map(event -> eventMapper.toShortDto(event, categoryMapper.toDto(
                        event.getCategory()), userRepository.findUserById(event.getInitiator().getId())))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(long eventId, HttpServletRequest request) {

        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED).orElseThrow(() ->
                new NotFoundException("События с таким id = " + eventId + " не найдено"));

        stats.hitStatistics(request);

        return eventMapper.toDto(
                event, categoryMapper.toDto(event.getCategory()), userRepository.findUserById(event.getInitiator().getId()));
    }
}
