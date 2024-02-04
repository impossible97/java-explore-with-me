package ru.practicum.private_api.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.event.service.PrivateEventService;
import ru.practicum.private_api.request.dto.RequestDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @PostMapping
    public EventDto createEvent(@PathVariable long userId, @RequestBody EventDto eventDto) {
        return privateEventService.createEvent(userId, eventDto);
    }

    @GetMapping
    public List<EventDto> getAllEventsByUser(@PathVariable long userId,
                                          @RequestParam(name = "from", defaultValue = "0") int from,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return privateEventService.getAllEventsByUser(userId, from, size);
    }

    @GetMapping("{eventId}")
    public EventDto getEventByUser(@PathVariable(name = "userId") long userId,
                                   @PathVariable(name = "eventId") long eventId) {
        return privateEventService.getEventByUser(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventDto patchEventByUser(@PathVariable(name = "userId") long userId,
                                     @PathVariable(name = "eventId") long eventId,
                                     @RequestBody EventDto eventDto) {
        return privateEventService.patchEventByUser(userId, eventId, eventDto);
    }

    @GetMapping("{eventId}/requests")
    public List<EventDto> getRequests(@PathVariable(name = "userId") long userId,
                                      @PathVariable(name = "eventId") long eventId) {
        return privateEventService.getRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public List<RequestDto> changeRequestStatus(@PathVariable(name = "userId") long userId,
                                                @PathVariable(name = "eventId") long eventId) {
        return privateEventService.changeRequestStatus(userId, eventId);
    }
}
