package ru.practicum.private_api.requests.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.requests.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {

    long id;
    LocalDateTime created;
    long event;
    long requester;
    RequestStatus status;
}
