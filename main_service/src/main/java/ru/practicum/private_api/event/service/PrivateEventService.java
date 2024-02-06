package ru.practicum.private_api.event.service;

import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.event.dto.NewEventDto;
import ru.practicum.private_api.event.dto.ShortEventDto;
import ru.practicum.private_api.request.dto.RequestDto;
import ru.practicum.private_api.request.model.ChangeStatusRequestResult;
import ru.practicum.private_api.request.model.ChangeStatusRequests;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PrivateEventService {
    EventDto createEvent(long userId, NewEventDto newEventDto);

    List<ShortEventDto> getAllEventsByUser(long userId, int from, int size, HttpServletRequest httpServletRequest);

    EventDto getEventByUser(long userId, long eventId, HttpServletRequest httpServletRequest);

    EventDto patchEventByUser(long userId, long eventId, NewEventDto eventDto);

    List<RequestDto> getRequests(long userId, long eventId);

    List<ChangeStatusRequestResult> changeRequestStatus(long userId, long eventId, ChangeStatusRequests changeStatusRequests);
}
