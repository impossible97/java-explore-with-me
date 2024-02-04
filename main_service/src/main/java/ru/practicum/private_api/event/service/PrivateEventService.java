package ru.practicum.private_api.event.service;

import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.request.dto.RequestDto;

import java.util.List;

public interface PrivateEventService {
    EventDto createEvent(long userId, EventDto eventDto);

    List<EventDto> getAllEventsByUser(long userId, int from, int size);

    EventDto getEventByUser(long userId, long eventId);

    EventDto patchEventByUser(long userId, long eventId, EventDto eventDto);

    List<EventDto> getRequests(long userId, long eventId);

    List<RequestDto> changeRequestStatus(long userId, long eventId);
}
