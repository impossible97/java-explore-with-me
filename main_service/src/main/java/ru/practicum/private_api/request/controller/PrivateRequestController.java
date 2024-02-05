package ru.practicum.private_api.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.private_api.request.dto.RequestDto;
import ru.practicum.private_api.request.service.PrivateRequestService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users/{userId}/requests")
public class PrivateRequestController {

    private final PrivateRequestService privateRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable(name = "userId") long userId,
                                    @RequestParam(name = "eventId") long eventId) {

        return privateRequestService.createRequest(userId, eventId);
    }

    @GetMapping
    public List<RequestDto> getUserRequests(@PathVariable(name = "userId") long userId) {

        return privateRequestService.getUserRequests(userId);
    }

    @PatchMapping("{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable(name = "userId") long userId,
                                    @PathVariable(name = "requestId") long requestId) {
        return privateRequestService.cancelRequest(userId, requestId);
    }
}
