package ru.practicum.admin_api.events.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_api.categories.repository.CategoryRepository;
import ru.practicum.admin_api.events.dto.StateAction;
import ru.practicum.admin_api.events.dto.UpdateEventDto;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;
import ru.practicum.private_api.events.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Transactional(readOnly = true)
    @Override
    public List<EventDto> searchEvents(
            List<Long> users, List<EventState> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size) {

        List<Event> events;

        if (users != null && states != null && categories != null && rangeStart != null && rangeEnd != null) {
            LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
            LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);

            events =  eventRepository.findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                    users, states, categories, start, end, PageRequest.of(from / size, size)
            );
        } else {
            if (users != null && states != null && categories != null) {
                events = eventRepository.findAllByInitiatorIdInAndStateInAndCategoryIdIn(
                        users, states, categories, PageRequest.of(from / size, size)
                );
            } else if (users != null && states != null) {
                events = eventRepository.findAllByInitiatorIdInAndStateIn(users, states, PageRequest.of(from / size, size));
            } else if (users != null) {
                events = eventRepository.findAllByInitiatorIdIn(users, PageRequest.of(from / size, size));
            } else {
                events = eventRepository.findAll(PageRequest.of(from / size, size)).toList();
            }
        }
        return events.stream().map(event ->
                        eventMapper.toDto(event,
                                categoryMapper.toDto(event.getCategory()),
                                userRepository.findUserById(event.getInitiator().getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventDto editEventDateAndState(long eventId, UpdateEventDto updateEventDto) {

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Собития с таким id = " + eventId + " не найдено"));


        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации");
        }
        if (LocalDateTime.now().plusHours(1).isAfter(event.getEventDate())) {
            throw new ConflictException("Событие не может быть изменено менее чем за час до начала");
        }

        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getCategory() != 0) {
            event.setCategory(categoryRepository.findCategoryById(updateEventDto.getCategory()));
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getEventDate() != null) {
            try {
                LocalDateTime time = LocalDateTime.parse(updateEventDto.getEventDate(), formatter);
                if (time.isBefore(LocalDateTime.now())) {
                    throw new ValidationException("Новая дата собития не может быть в прошлом");
                }
                event.setEventDate(time);
            } catch (DateTimeParseException e) {
                throw new ValidationException("Неверный формат даты и времени");
            }
        }
        if (updateEventDto.getLocation() != null) {
            Map<Float, Float> newLocation = new HashMap<>();
            newLocation.put(updateEventDto.getLocation().getLat(), updateEventDto.getLocation().getLon());
            event.setLocation(newLocation);
        }
        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != 0) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventDto.getRequestModeration());
        }
        if (updateEventDto.getStateAction() != null) {
            if (updateEventDto.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(EventState.PUBLISHED);
            } else {
                event.setState(EventState.CANCELED);
            }
        }
        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toDto(
                updatedEvent,
                categoryMapper.toDto(updatedEvent.getCategory()),
                userRepository.findUserById(updatedEvent.getInitiator().getId()));
    }
}
