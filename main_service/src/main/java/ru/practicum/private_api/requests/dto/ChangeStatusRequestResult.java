package ru.practicum.private_api.requests.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeStatusRequestResult {

    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}
