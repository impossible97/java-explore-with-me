package ru.practicum.private_api.event.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.categories.repository.CategoryRepository;
import ru.practicum.admin_api.user.model.User;
import ru.practicum.admin_api.user.repository.UserRepository;
import ru.practicum.client.BaseClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.event.dto.NewEventDto;
import ru.practicum.private_api.event.dto.ShortEventDto;
import ru.practicum.private_api.event.model.Event;
import ru.practicum.private_api.event.model.EventState;
import ru.practicum.private_api.event.repository.EventRepository;
import ru.practicum.private_api.request.dto.RequestDto;
import ru.practicum.private_api.request.model.ChangeStatusRequestResult;
import ru.practicum.private_api.request.model.ChangeStatusRequests;
import ru.practicum.private_api.request.model.RequestStatus;
import ru.practicum.private_api.request.repository.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final BaseClient baseClient;
    private final RequestMapper requestMapper;

    @Override
    public EventDto createEvent(long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() ->
                new NotFoundException("Категория с таким id = " + newEventDto.getCategory() + " не найдена"));
        Event event = eventMapper.toEntity(newEventDto, user, category);
        event.setState(EventState.PENDING);

        return eventMapper.toDto(eventRepository.save(event),
                categoryMapper.toDto(category),
                userRepository.findUserById(userId));
    }

    @Override
    public List<ShortEventDto> getAllEventsByUser(long userId, int from, int size, HttpServletRequest httpServletRequest) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));

        hitStatistics(httpServletRequest);


        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size), Sort.by("eventDate").descending())
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

    @Override
    public EventDto getEventByUser(long userId, long eventId, HttpServletRequest httpServletRequest) {
        Event event = validateEvent(userId, eventId);

        hitStatistics(httpServletRequest);
        event.setViews(event.getViews() + 1);
        Event savedEvent = eventRepository.save(event);

        return eventMapper.toDto(savedEvent, categoryMapper.toDto(savedEvent.getCategory()), userRepository.findUserById(userId));
    }

    @Override
    public EventDto patchEventByUser(long userId, long eventId, NewEventDto newEventDto) {
        Event event = validateEvent(userId, eventId);

        if (!(event.getState().equals(EventState.CANCELED) || event.getState().equals(EventState.PENDING))) {
             throw new ConflictException("Опубликованное событие не может быть изменено");
        }
        LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (eventDate.isBefore(LocalDateTime.now().plusHours(2)) || eventDate.equals(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Событие не может быть изменено менее чем за два часа до начала");
        }
        if (newEventDto.getTitle() != null) {
            event.setTitle(newEventDto.getTitle());
        }
        if (newEventDto.getAnnotation() != null) {
            event.setAnnotation(newEventDto.getAnnotation());
        }
        if (newEventDto.getCategory() != event.getCategory().getId()) {
            event.setCategory(categoryRepository.findCategoryById(newEventDto.getCategory()));
        }
        if (newEventDto.getDescription() != null) {
            event.setDescription(newEventDto.getDescription());
        }
        if (newEventDto.getEventDate().equals(event.getEventDate())) {
            event.setEventDate(newEventDto.getEventDate());
        }
        if (newEventDto.getLocation() != eventMapper.mapLocation(event.getLocation())) {
            event.setLocation(Map.of(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()));
        }
        if (newEventDto.isPaid() != event.isPaid()) {
            event.setPaid(newEventDto.isPaid());
        }
        if (newEventDto.getParticipantLimit() != event.getParticipantLimit()) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        }
        if (newEventDto.isRequestModeration() != event.isRequestModeration()) {
            event.setRequestModeration(newEventDto.isRequestModeration());
        }

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toDto(updatedEvent, categoryMapper.toDto(updatedEvent.getCategory()), userRepository.findUserById(userId));
    }

    @Override
    public List<RequestDto> getRequests(long userId, long eventId) {
        validateEvent(userId, eventId);

        return requestRepository.findAllByEventId(eventId).stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChangeStatusRequestResult> changeRequestStatus(long userId, long eventId, ChangeStatusRequests changeStatusRequests) {
        Event event = validateEvent(userId, eventId);
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ConflictException("У события достигнут лимит запросов на участие");
        }

        List<ChangeStatusRequestResult> result = new ArrayList<>();

        List<RequestDto> requestDtos = requestRepository.findAllByEventIdAndIdIn(eventId, changeStatusRequests.getRequestIds()).stream()
                .map(request -> {
                    if (request.getStatus().equals(RequestStatus.PENDING)) { // Статус равен "Ожидание"
                        if (event.getParticipantLimit() != 0 || !event.isRequestModeration()) {
                            if (event.getConfirmedRequests() < event.getParticipantLimit()) { // Лимит не достигнут
                                request.setStatus(changeStatusRequests.getStatus());
                                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                            } else {
                                request.setStatus(RequestStatus.CANCELED);
                            }
                        } else {
                            request.setStatus(RequestStatus.CONFIRMED);
                            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        }
                    } else {
                        throw new ConflictException("Статус можно изменить только у заявок, находящихся в состоянии ожидания." +
                                "У заявки с id = " + request.getId() + " статус " + request.getStatus() + ".");
                    }
                    requestRepository.save(request);
                    if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
                        result.add()
                    }
                    return requestMapper.toDto(request);
                })
                .collect(Collectors.toList());

        eventRepository.save(event);

        return result;
    }

    private void hitStatistics(HttpServletRequest request) {
        baseClient.post("/hit", EndpointHitDto.builder()
                .app("main_service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build());
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
