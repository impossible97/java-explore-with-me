package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.users.model.User;
import ru.practicum.private_api.events.model.Event;
import ru.practicum.private_api.requests.dto.RequestDto;
import ru.practicum.private_api.requests.model.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RequestMapper {

    public Request toEntity(RequestDto requestDto, Event event, User requester) {
        Request request = new Request();

        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(requester);
        request.setStatus(requestDto.getStatus());

        return  request;
    }

    public RequestDto toDto(Request request) {
        RequestDto requestDto = new RequestDto();

        requestDto.setId(request.getId());
        requestDto.setCreated(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setStatus(request.getStatus());

        return requestDto;
    }
}
