package ru.practicum.private_api.events.service;

import ru.practicum.admin_api.events.dto.UpdateEventDto;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.NewEventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.requests.dto.ChangeStatusRequestResult;
import ru.practicum.private_api.requests.dto.ChangeStatusRequests;
import ru.practicum.private_api.requests.dto.RequestDto;

import java.util.List;

public interface PrivateEventService {
    EventDto createEvent(long userId, NewEventDto newEventDto);

    List<ShortEventDto> getAllEventsByUser(long userId, int from, int size);

    EventDto getEventByUser(long userId, long eventId);

    EventDto patchEventByUser(long userId, long eventId, UpdateEventDto updateEventDto);

    List<RequestDto> getRequests(long userId, long eventId);

    ChangeStatusRequestResult changeRequestStatus(long userId, long eventId, ChangeStatusRequests changeStatusRequests);
}
