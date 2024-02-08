package ru.practicum.private_api.requests.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.private_api.requests.model.RequestStatus;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeStatusRequests {

    List<Long> requestIds;
    RequestStatus status;
}
