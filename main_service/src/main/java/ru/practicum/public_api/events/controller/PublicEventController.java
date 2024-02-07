package ru.practicum.public_api.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.private_api.events.dto.EventDto;
import ru.practicum.private_api.events.dto.ShortEventDto;
import ru.practicum.public_api.events.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("events")
public class PublicEventController {

    private final PublicEventService publicEventService;

    @GetMapping
    public List<ShortEventDto> getEventsWithDescription(
            @RequestParam(name = "text") String text,
            @RequestParam(name = "categories") List<Long> categories,
            @RequestParam(name = "paid") boolean paid,
            @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(name = "onlyAvailable", defaultValue = "false") boolean onlyAvailable,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "from") int from,
            @RequestParam(name = "size") int size,
            HttpServletRequest request) {

        return publicEventService.getEventsWithDescription(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("{eventId}")
    public EventDto getEventById(@PathVariable long eventId, HttpServletRequest request) {
        return publicEventService.getEventById(eventId, request);
    }
}
