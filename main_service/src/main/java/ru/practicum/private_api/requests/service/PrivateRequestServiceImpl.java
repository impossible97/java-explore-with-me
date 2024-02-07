package ru.practicum.private_api.requests.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.admin_api.users.repository.UserRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.events.model.EventState;
import ru.practicum.private_api.events.repository.EventRepository;
import ru.practicum.private_api.requests.dto.RequestDto;
import ru.practicum.private_api.requests.model.Request;
import ru.practicum.private_api.requests.model.RequestStatus;
import ru.practicum.private_api.requests.repository.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;


    @Override
    public RequestDto createRequest(long userId, long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Собития с таким id = " + eventId + " не найдено"));

        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new ConflictException("Запрос на участие к событию с id = "
                    + eventId + " от пользователя с id = " + userId + " уже существует");
        }
        if (userId == event.getInitiator().getId()) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ConflictException("У события достигнут лимит запросов на участие");
        }
        RequestDto requestDto = new RequestDto();

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            requestDto.setStatus(RequestStatus.CONFIRMED);
        } else {
            requestDto.setStatus(RequestStatus.PENDING);
        }
        return requestMapper.toDto(requestRepository.save(requestMapper.toEntity(requestDto, event, user)));
    }

    @Override
    public List<RequestDto> getUserRequests(long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));

        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto cancelRequest(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким id = " + userId + " не найден"));
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Запрос с id = " + requestId + " не найден"));

        request.setStatus(RequestStatus.CANCELED);

        return requestMapper.toDto(requestRepository.save(request));
    }
}
