package ru.practicum.private_api.request.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeStatusRequests {

    List<Long> requestIds;
    RequestStatus status;
}
