package ru.practicum.private_api.requests.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.requests.model.RequestStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {

    long id;
    String created;
    long event;
    long requester;
    RequestStatus status;
}
