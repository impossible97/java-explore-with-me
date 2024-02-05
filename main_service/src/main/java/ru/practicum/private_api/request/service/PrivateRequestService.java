package ru.practicum.private_api.request.service;

import ru.practicum.private_api.request.dto.RequestDto;

import java.util.List;

public interface PrivateRequestService {
    RequestDto createRequest(long userId, long eventId);

    List<RequestDto> getUserRequests(long userId);

    RequestDto cancelRequest(long userId, long requestId);
}
