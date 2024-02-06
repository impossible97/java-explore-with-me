package ru.practicum.admin_api.events.service;

import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.model.EventState;

import java.util.List;

public interface AdminEventService {
    List<EventDto> searchEvents(
            List<Long> users, List<EventState> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size);
}
