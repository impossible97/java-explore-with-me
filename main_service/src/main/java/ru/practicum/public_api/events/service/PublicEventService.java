package ru.practicum.public_api.events.service;

import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    List<ShortEventDto> getEventsWithDescription(
            String text, List<Long> categories, boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
            String sort, int from, int size, HttpServletRequest request);

    EventDto getEventById(long eventId, HttpServletRequest request);
}
