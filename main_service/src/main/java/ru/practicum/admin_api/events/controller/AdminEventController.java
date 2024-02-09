package ru.practicum.admin_api.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.events.dto.UpdateEventDto;
import ru.practicum.admin_api.events.service.AdminEventService;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.model.EventState;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventDto> searchEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                       @RequestParam(name = "states", required = false) List<EventState> states,
                                       @RequestParam(name = "categories", required = false) List<Long> categories,
                                       @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                       @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                       @RequestParam(value = "from", defaultValue = "0") int from,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        return adminEventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("{eventId}")
    public EventDto editEventDate(@PathVariable long eventId, @Validated @RequestBody UpdateEventDto updateEventDto) {
        return adminEventService.editEventDateAndState(eventId, updateEventDto);
    }
}