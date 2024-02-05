package ru.practicum.private_api.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.private_api.event.dto.EventDto;
import ru.practicum.private_api.event.dto.NewEventDto;
import ru.practicum.private_api.event.dto.ShortEventDto;
import ru.practicum.private_api.event.service.PrivateEventService;
import ru.practicum.private_api.request.dto.RequestDto;
import ru.practicum.private_api.request.model.ChangeStatusRequests;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users/{userId}/events")
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@PathVariable long userId, @RequestBody NewEventDto newEventDto) {
        return privateEventService.createEvent(userId, newEventDto);
    }

    @GetMapping
    public List<ShortEventDto> getAllEventsByUser(@PathVariable long userId,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                                  HttpServletRequest httpServletRequest) {

        return privateEventService.getAllEventsByUser(userId, from, size, httpServletRequest);
    }

    @GetMapping("{eventId}")
    public EventDto getEventByUser(@PathVariable(name = "userId") long userId,
                                   @PathVariable(name = "eventId") long eventId,
                                   HttpServletRequest httpServletRequest) {
        return privateEventService.getEventByUser(userId, eventId, httpServletRequest);
    }

    @PatchMapping("{eventId}")
    public EventDto patchEventByUser(@PathVariable(name = "userId") long userId,
                                     @PathVariable(name = "eventId") long eventId,
                                     @RequestBody NewEventDto newEventDto) {
        return privateEventService.patchEventByUser(userId, eventId, newEventDto);
    }

    @GetMapping("{eventId}/requests")
    public List<EventDto> getRequests(@PathVariable(name = "userId") long userId,
                                      @PathVariable(name = "eventId") long eventId) {
        return privateEventService.getRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public List<RequestDto> changeRequestStatus(@PathVariable(name = "userId") long userId,
                                                @PathVariable(name = "eventId") long eventId,
                                                @RequestBody ChangeStatusRequests changeStatusRequests) {
        return privateEventService.changeRequestStatus(userId, eventId, changeStatusRequests);
    }
}
