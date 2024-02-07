package ru.practicum.private_api.events.service;

import ru.practicum.admin_api.events.dto.UpdateEventDto;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.NewEventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.requests.dto.RequestDto;
import ru.practicum.private_api.requests.model.ChangeStatusRequestResult;
import ru.practicum.private_api.requests.model.ChangeStatusRequests;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PrivateEventService {
    EventDto createEvent(long userId, NewEventDto newEventDto);

    List<ShortEventDto> getAllEventsByUser(long userId, int from, int size, HttpServletRequest httpServletRequest);

    EventDto getEventByUser(long userId, long eventId, HttpServletRequest httpServletRequest);

    EventDto patchEventByUser(long userId, long eventId, UpdateEventDto updateEventDto);

    List<RequestDto> getRequests(long userId, long eventId);

    ChangeStatusRequestResult changeRequestStatus(long userId, long eventId, ChangeStatusRequests changeStatusRequests);
}
