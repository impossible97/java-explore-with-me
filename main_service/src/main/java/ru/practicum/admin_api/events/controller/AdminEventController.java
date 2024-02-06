package ru.practicum.admin_api.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public List<EventDto> searchEvents(@RequestParam(name = "users") List<Long> users,
                                       @RequestParam(name = "states") List<EventState> states,
                                       @RequestParam(name = "categories") List<Long> categories,
                                       @RequestParam("rangeStart") String rangeStart,
                                       @RequestParam("rangeEnd") String rangeEnd,
                                       @RequestParam(value = "from", defaultValue = "0") int from,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        return adminEventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
