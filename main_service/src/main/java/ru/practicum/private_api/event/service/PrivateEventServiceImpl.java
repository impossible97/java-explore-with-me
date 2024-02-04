package ru.practicum.private_api.event.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.EventMapper;
import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.event.repository.EventRepository;
import ru.practicum.private_api.request.dto.RequestDto;

import java.util.List;

@Service
@AllArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Override
    public EventDto createEvent(long userId, EventDto eventDto) {
        return null;
    }

    @Override
    public List<EventDto> getAllEventsByUser(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventDto getEventByUser(long userId, long eventId) {
        return null;
    }

    @Override
    public EventDto patchEventByUser(long userId, long eventId, EventDto eventDto) {
        return null;
    }

    @Override
    public List<EventDto> getRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public List<RequestDto> changeRequestStatus(long userId, long eventId) {
        return null;
    }
}
