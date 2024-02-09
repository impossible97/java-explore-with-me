package ru.practicum.private_api.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.events.dto.UpdateEventDto;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.NewEventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.private_api.events.service.PrivateEventService;
import ru.practicum.private_api.requests.dto.ChangeStatusRequestResult;
import ru.practicum.private_api.requests.dto.ChangeStatusRequests;
import ru.practicum.private_api.requests.dto.RequestDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users/{userId}/events")
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@PathVariable long userId, @Validated @RequestBody NewEventDto newEventDto) {
        return privateEventService.createEvent(userId, newEventDto);
    }

    @GetMapping
    public List<ShortEventDto> getAllEventsByUser(@PathVariable long userId,
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
                                     @Validated @RequestBody UpdateEventDto updateEventDto) {
        return privateEventService.patchEventByUser(userId, eventId, updateEventDto);
    }

    @GetMapping("{eventId}/requests")
    public List<RequestDto> getRequests(@PathVariable(name = "userId") long userId,
                                      @PathVariable(name = "eventId") long eventId) {
        return privateEventService.getRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public ChangeStatusRequestResult changeRequestStatus(@PathVariable(name = "userId") long userId,
                                                               @PathVariable(name = "eventId") long eventId,
                                                               @RequestBody ChangeStatusRequests changeStatusRequests) {
        return privateEventService.changeRequestStatus(userId, eventId, changeStatusRequests);
    }
}
