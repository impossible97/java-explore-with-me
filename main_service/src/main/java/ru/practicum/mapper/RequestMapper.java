package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.user.model.User;
import ru.practicum.private_api.event.model.Event;
import ru.practicum.private_api.request.dto.RequestDto;
import ru.practicum.private_api.request.model.Request;

@Component
public class RequestMapper {

    public Request toEntity(RequestDto requestDto, Event event, User requester) {
        Request request = new Request();

        request.setCreated(requestDto.getCreated());
        request.setEvent(event);
        request.setRequester(requester);
        request.setStatus(requestDto.getStatus());

        return  request;
    }

    public RequestDto toDto(Request request) {
        RequestDto requestDto = new RequestDto();

        requestDto.setId(request.getId());
        requestDto.setCreated(request.getCreated());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setStatus(request.getStatus());

        return requestDto;
    }
}
