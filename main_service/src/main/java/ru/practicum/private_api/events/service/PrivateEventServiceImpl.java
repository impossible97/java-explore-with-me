package ru.practicum.private_api.events.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.categories.repository.CategoryRepository;
import ru.practicum.admin_api.events.dto.StateAction;
import ru.practicum.admin_api.events.dto.UpdateEventDto;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.NewEventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;
import ru.practicum.private_api.events.repository.EventRepository;
import ru.practicum.private_api.requests.dto.ChangeStatusRequestResult;
import ru.practicum.private_api.requests.dto.ChangeStatusRequests;
import ru.practicum.private_api.requests.dto.RequestDto;
import ru.practicum.private_api.requests.model.RequestStatus;
import ru.practicum.private_api.requests.repository.RequestRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final RequestMapper requestMapper;

    @Transactional
    @Override
    public EventDto createEvent(long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new NotFoundException("Категория с таким id = " + newEventDto.getCategory() + " не найдена"));
        try {
            LocalDateTime time = LocalDateTime.parse(newEventDto.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (time.isBefore(LocalDateTime.now())) {
                throw new ValidationException("Новая дата собития не может быть в прошлом");
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Неверный формат даты и времени");
        }
        if (newEventDto.getPaid() == null) {
            newEventDto.setPaid(false);
        }
        if (newEventDto.getRequestModeration() == null) {
            newEventDto.setRequestModeration(true);
        }
        Event event = eventMapper.toEntity(newEventDto, user, category);
        event.setState(EventState.PENDING);

        return eventMapper.toDto(eventRepository.save(event),
                categoryMapper.toDto(category),
                userRepository.findUserById(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShortEventDto> getAllEventsByUser(long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));

        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size, Sort.by("eventDate").descending()))
                .stream()
                .map(event -> {
                    event.setViews(event.getViews() + 1);
                    Event savedEvent = eventRepository.save(event);
                    return eventMapper.toShortDto(savedEvent,
                            categoryMapper.toDto(savedEvent.getCategory()),
                            userRepository.findUserById(userId));
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventDto getEventByUser(long userId, long eventId) {
        Event event = validateEvent(userId, eventId);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toDto(savedEvent, categoryMapper.toDto(savedEvent.getCategory()), userRepository.findUserById(userId));
    }

    @Transactional
    @Override
    public EventDto patchEventByUser(long userId, long eventId, UpdateEventDto updateEventDto) {
        Event event = validateEvent(userId, eventId);

        if (event.getState().equals(EventState.PUBLISHED)) {
             throw new ConflictException("Опубликованное событие не может быть изменено");
        }

        if (LocalDateTime.now().plusHours(2).isAfter(event.getEventDate())) {
            throw new ConflictException("Событие не может быть изменено менее чем за два часа до начала");
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
                event.setEventDate(LocalDateTime.parse(updateEventDto.getEventDate()));
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
            if (updateEventDto.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            } else {
                event.setState(EventState.PENDING);
            }
        }

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toDto(updatedEvent, categoryMapper.toDto(updatedEvent.getCategory()), userRepository.findUserById(userId));
    }


    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> getRequests(long userId, long eventId) {
        validateEvent(userId, eventId);

        return requestRepository.findAllByEventId(eventId).stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ChangeStatusRequestResult changeRequestStatus(long userId, long eventId, ChangeStatusRequests changeStatusRequests) {
        Event event = validateEvent(userId, eventId);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("У события достигнут лимит запросов на участие");
        }
        List<RequestDto> requestDtos = requestRepository.findAllByEventIdAndIdIn(eventId, changeStatusRequests.getRequestIds()).stream()
                .map(request -> {
                    if (request.getStatus().equals(RequestStatus.PENDING)) { // Статус равен "Ожидание"
                        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
                            request.setStatus(RequestStatus.CONFIRMED);
                            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        } else {
                            if (changeStatusRequests.getStatus().equals(RequestStatus.CONFIRMED) && event.getParticipantLimit() != event.getConfirmedRequests()) {
                                request.setStatus(RequestStatus.CONFIRMED);
                                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                            } else {
                                request.setStatus(RequestStatus.REJECTED);
                            }
                        }
                    } else {
                        throw new ConflictException("Статус можно изменить только у заявок, находящихся в состоянии ожидания." +
                                "У заявки с id = " + request.getId() + " статус " + request.getStatus() + ".");
                    }
                    requestRepository.save(request);
                    return requestMapper.toDto(request);
                })
                .collect(Collectors.toList());

        ChangeStatusRequestResult result = new ChangeStatusRequestResult();

        eventRepository.save(event);

        result.setConfirmedRequests(requestDtos.stream().filter(requestDto -> requestDto.getStatus().equals(RequestStatus.CONFIRMED))
                .collect(Collectors.toList()));

        result.setRejectedRequests(requestDtos.stream().filter(requestDto -> requestDto.getStatus().equals(RequestStatus.REJECTED))
                .collect(Collectors.toList()));

        return result;
    }

    private Event validateEvent(long userId, long eventId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Собития с таким id = " + eventId + " не найдено"));
        if (userId != event.getInitiator().getId()) {
            throw new NotFoundException("Пользователь с id + " + userId + " не является создателем события с id = " + eventId);
        }
        return event;
    }
}
